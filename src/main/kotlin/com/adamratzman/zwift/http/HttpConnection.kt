/* Spotify Web API, Kotlin Wrapper; MIT License, 2017-2020; Original author: Adam Ratzman */
package com.adamratzman.zwift.http

import com.adamratzman.zwift.ZwiftClient
import com.adamratzman.zwift.ZwiftException
import com.adamratzman.zwift.ZwiftExceptionModel
import io.ktor.client.HttpClient
import io.ktor.client.features.ResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.statement.readBytes
import io.ktor.client.statement.readText
import io.ktor.client.utils.EmptyContent
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.content.ByteArrayContent
import io.ktor.http.content.TextContent
import io.ktor.http.contentType
import io.ktor.utils.io.charsets.MalformedInputException
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.stringify

private val stableJson = Json(JsonConfiguration.Stable)

enum class HttpRequestMethod(internal val externalMethod: HttpMethod) {
    GET(HttpMethod.Get),
    POST(HttpMethod.Post),
    PUT(HttpMethod.Put),
    DELETE(HttpMethod.Delete);
}

data class HttpHeader(val key: String, val value: String)

data class HttpResponse(val responseCode: Int, val body: String, val headers: List<HttpHeader>)

/**
 * Provides a fast, easy, and slim way to execute and retrieve HTTP GET, POST, PUT, and DELETE requests
 */
class HttpConnection constructor(
    val url: String,
    val method: HttpRequestMethod,
    val bodyMap: Map<*, *>?,
    val bodyString: String?,
    contentType: String?,
    val headers: List<HttpHeader> = listOf(),
    val api: ZwiftClient? = null
) {
    val contentType: ContentType? = contentType?.let { ContentType.parse(it) }

    companion object {
        private val client = HttpClient()
    }

    fun String?.toByteArrayContent(): ByteArrayContent? {
        return if (this == null) null else ByteArrayContent(this.toByteArray(), contentType)
    }

    fun buildRequest(additionalHeaders: List<HttpHeader>?): HttpRequestBuilder = HttpRequestBuilder().apply {
        url(this@HttpConnection.url)
        method = this@HttpConnection.method.externalMethod

        body = when (this@HttpConnection.method) {
            HttpRequestMethod.DELETE -> {
                bodyString.toByteArrayContent() ?: body
            }
            HttpRequestMethod.PUT, HttpRequestMethod.POST -> {
                val contentString = if (contentType == ContentType.Application.FormUrlEncoded) {
                    bodyMap?.map { "${it.key}=${it.value}" }?.joinToString("&") ?: bodyString
                } else bodyString

                contentString.toByteArrayContent() ?: ByteArrayContent("".toByteArray(), contentType)
            }
            else -> body
        }


        if (body === EmptyContent && this@HttpConnection.method != HttpRequestMethod.POST) {
            body = TextContent("", ContentType.Application.Json)
        }

        // let additionalHeaders overwrite headers
        val allHeaders = this@HttpConnection.headers + (additionalHeaders ?: listOf())

        allHeaders.forEach { (key, value) ->
            header(key, value)
        }
    }

    suspend fun executeByte(
        additionalHeaders: List<HttpHeader>? = null,
        currentExecution: Int = 0
    ): ByteArray {
        val httpRequest = buildRequest(additionalHeaders)

        try {
            return client.request<io.ktor.client.statement.HttpResponse>(httpRequest).let { response ->
                val respCode = response.status.value
                try {
                    val bytes = response.readBytes()

                    if (respCode in listOf(400, 401) && api?.authToken?.hasValidRefreshToken() == false) {
                        api.refreshToken()
                        val newAdditionalHeaders = additionalHeaders?.toMutableList() ?: mutableListOf()
                        newAdditionalHeaders.add(
                            0,
                            HttpHeader(
                                "Authorization",
                                "Bearer ${api.authToken.accessToken}"
                            )
                        )
                        return executeByte(newAdditionalHeaders)
                    }

                    return bytes
                } catch (e: MalformedInputException) {
                    if (currentExecution > 10) throw e
                    return executeByte(additionalHeaders, currentExecution + 1)
                }
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: ResponseException) {
            val errorBody = e.response.readText()
            val error = (api?.json ?: stableJson).parse(ZwiftExceptionModel.serializer(), errorBody)
            throw ZwiftException(error, e.response.status.value)
        }
    }

    suspend fun execute(
        additionalHeaders: List<HttpHeader>? = null,
        readBytes: Boolean = false,
        currentExecution: Int = 0
    ): HttpResponse {
        val httpRequest = buildRequest(additionalHeaders)
        try {
            return client.request<io.ktor.client.statement.HttpResponse>(httpRequest).let { response ->
                val respCode = response.status.value
                try {
                    val body = if (readBytes) response.readBytes().toString(Charsets.UTF_8) else response.readText()

                    if (respCode in listOf(400, 401) && api?.authToken?.hasValidRefreshToken() == false) {
                        api.refreshToken()
                        val newAdditionalHeaders = additionalHeaders?.toMutableList() ?: mutableListOf()
                        newAdditionalHeaders.add(
                            0,
                            HttpHeader(
                                "Authorization",
                                "Bearer ${api.authToken.accessToken}"
                            )
                        )
                        return execute(newAdditionalHeaders, readBytes)
                    }

                    return HttpResponse(
                        responseCode = respCode,
                        body = body,
                        headers = response.headers.entries().map { (key, value) ->
                            HttpHeader(
                                key,
                                value.getOrNull(0) ?: "null"
                            )
                        }
                    )
                } catch (e: MalformedInputException) {
                    if (currentExecution > 10) throw e
                    return execute(additionalHeaders, readBytes, currentExecution + 1)
                }
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: ResponseException) {
            e.printStackTrace()
            val errorBody = e.response.readText()
            val error = (api?.json ?: stableJson).parse(ZwiftExceptionModel.serializer(), errorBody)
            throw ZwiftException(error, e.response.status.value)
        }
    }

    override fun toString(): String {
        return """HttpConnection  (
            |url=$url,
            |method=$method,
            |body=${bodyString ?: bodyMap},
            |contentType=$contentType,
            |headers=${headers.toList()}
            |  )""".trimMargin()
    }
}

enum class HttpConnectionStatus(val code: Int) {
    HTTP_NOT_MODIFIED(304);
}

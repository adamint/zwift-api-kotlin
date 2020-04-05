package com.adamratzman.zwift.http

import com.adamratzman.zwift.ZwiftClient
import com.adamratzman.zwift.ZwiftException
import com.adamratzman.zwift.ZwiftExceptionModel
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.protobuf.ProtoBuf

abstract class ZwiftEndpoint(
    val client: ZwiftClient,
    val jsonSerializer: Json = client.json,
    val protoBuf: ProtoBuf = ProtoBuf()
) {
    private val baseUrl = "https://us-or-rly101.zwift.com"
    suspend fun execute(
        path: String,
        method: HttpRequestMethod,
        data: Map<String, Any?>? = null,
        bodyString:String?=null,
        contentType: String? = null,
        additionalHeaders: List<HttpHeader>? = null,
        byteArray: Boolean = false
    ): HttpResponse {
        return HttpConnection(
            "$baseUrl$path",
            method,
            data,
            bodyString,
            contentType,
            listOf(
                HttpHeader(
                    "Authorization",
                    "Bearer ${client.authToken.accessToken}"
                ),
                HttpHeader(
                    "User-Agent",
                    "Zwift/115 CFNetwork/758.0.2 Darwin/15.0.0"
                )
            )
        ).execute(additionalHeaders, byteArray)
    }

    suspend fun executeByte(
        path: String,
        method: HttpRequestMethod,
        data: Map<String, String>? = null,
        contentType: String? = null,
        additionalHeaders: List<HttpHeader>? = null
    ): ByteArray {
        return HttpConnection(
            "$baseUrl$path",
            method,
            data,
            null,
            contentType,
            listOf(
                HttpHeader(
                    "Authorization",
                    "Bearer ${client.authToken.accessToken}"
                ),
                HttpHeader(
                    "User-Agent",
                    "Zwift/115 CFNetwork/758.0.2 Darwin/15.0.0"
                )
            )
        ).executeByte(additionalHeaders)
    }

    fun <T> ByteArray.deserializeProtobuf(serializer: KSerializer<T>): T = protoBuf.load(serializer, this)

    fun <T> HttpResponse.deserializeJson(serializer: KSerializer<T>): T {
        try {
            if (responseCode == 200) return jsonSerializer.parse(serializer, body)
            else {
                val exceptionModel = jsonSerializer.parse(ZwiftExceptionModel.serializer(), body)
                throw ZwiftException(
                    exceptionModel.error,
                    exceptionModel.errorDescription,
                    responseCode
                )
            }
        } catch (e: Exception) {
            throw ZwiftException(
                "Parse error ($responseCode)",
                "Can't parse body",
                responseCode,
                e
            )
        }
    }
}

internal fun jsonMap(vararg pairs: Pair<String, JsonElement>) = pairs.toMap().toMutableMap()
fun Map<String,JsonElement>.toJson() = JsonObject(this).toString()

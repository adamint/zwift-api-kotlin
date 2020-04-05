package com.adamratzman.zwift

import com.adamratzman.zwift.http.HttpConnection
import com.adamratzman.zwift.http.HttpRequestMethod
import io.ktor.http.ContentType
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

class ZwiftClient(
    var authToken: AuthToken,
    @Suppress("EXPERIMENTAL_API_USAGE") val json: Json = Json(
        JsonConfiguration(
            isLenient = true,
            ignoreUnknownKeys = true,
            serializeSpecialFloatingPointValues = true,
            useArrayPolymorphism = true
        )
    )
) {
    constructor(
        username: String, password: String, @Suppress("EXPERIMENTAL_API_USAGE") json: Json = Json(
            JsonConfiguration(
                isLenient = true,
                ignoreUnknownKeys = true,
                serializeSpecialFloatingPointValues = true,
                useArrayPolymorphism = true
            )
        )
    ) : this(runBlocking { getAuthToken(username, password, json) })

    val profileApi: ZwiftProfileApi = ZwiftProfileApi(this)
    val activityApi: ZwiftActivityApi = ZwiftActivityApi(this)
val worldApi:ZwiftWorldApi=ZwiftWorldApi(this)
    suspend fun refreshToken() {
        authToken = getAuthToken(null, null, json, authToken)
    }

}

@Serializable
data class AuthToken(
    @SerialName("access_token") val accessToken: String,
    @SerialName("expires_in") val expiresIn: Int,
    @SerialName("refresh_expires_in") val refreshExpiresIn: Int,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("token_type") val tokenType: String,
    @SerialName("not-before-policy") val notBeforePolicy: Long,
    @SerialName("session_state") val sessionState: String
) {
    @Transient
    val creationTime: Long = System.currentTimeMillis()

    fun hasValidRefreshToken(): Boolean = System.currentTimeMillis() > creationTime + expiresIn * 1000
}

@Serializable
class ZwiftExceptionModel(
    val error: String,
    @SerialName("error_description") val errorDescription: String
)

data class ZwiftException(
    val error: String,
    val errorDescription: String,
    val statusCode: Int,
    override val cause: Throwable? = null
) :
    Exception("$error: $errorDescription (status code $statusCode)", cause) {

    constructor(model: ZwiftExceptionModel, statusCode: Int) : this(model.error, model.errorDescription, statusCode)
}

suspend fun getAuthToken(username: String?, password: String?, json: Json, currentToken: AuthToken? = null): AuthToken {
    val data = mutableMapOf<String, Any?>()
    if (currentToken?.hasValidRefreshToken() == true) {
        data["refresh_token"] = currentToken.refreshToken
        data["grant_type"] = "refresh_token"
    } else {
        data["username"] = username
        data["password"] = password
        data["grant_type"] = "password"
    }
    data["client_id"] = "Zwift_Mobile_Link"

    val response = HttpConnection(
        "https://secure.zwift.com/auth/realms/zwift/tokens/access/codes",
        HttpRequestMethod.POST,
        data,
        null,
        ContentType.Application.FormUrlEncoded.toString()
    ).execute()

    if (response.responseCode == 200) return json.parse(AuthToken.serializer(), response.body)
    else {
        val exceptionModel = json.parse(ZwiftExceptionModel.serializer(), response.body)
        throw ZwiftException(exceptionModel.error, exceptionModel.errorDescription, response.responseCode)
    }
}



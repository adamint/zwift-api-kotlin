package com.adamratzman.zwift

import com.adamratzman.zwift.http.HttpRequestMethod
import com.adamratzman.zwift.http.ZwiftEndpoint
import com.adamratzman.zwift.http.jsonMap
import com.adamratzman.zwift.http.toJson
import com.adamratzman.zwift.models.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.json

class ZwiftProfileApi(client: ZwiftClient) : ZwiftEndpoint(client) {
    val selfPlayerSymbolicId: String = "me"
    private var selfPersonalProfile = lazy { runBlocking { getProfileImpl() } }
    val playerId by lazy { selfPersonalProfile.value.id }

    suspend fun getAndUpdateSelfProfile(): ZwiftPersonalProfile {
        selfPersonalProfile = lazy { runBlocking { getProfileImpl() } }

        return getPersonalProfile()
    }

    fun getProfile() = selfPersonalProfile
    fun getPersonalProfile() = selfPersonalProfile.value


    suspend fun getOtherProfile(playerId: Int) = executeByte(
        "/api/profiles/$playerId",
        HttpRequestMethod.GET
    ).deserializeProtobuf(ZwiftOtherProfile.serializer())

    private suspend fun getProfileImpl(): ZwiftPersonalProfile {
        return execute(
            "/api/profiles/$selfPlayerSymbolicId",
            HttpRequestMethod.GET
        ).deserializeJson(ZwiftPersonalProfile.serializer())
    }

    suspend fun getFollowers() =
        execute(
            "/api/profiles/$playerId/followers",
            HttpRequestMethod.GET
        ).deserializeJson(ZwiftFollower.serializer().list)

    suspend fun getFollowees() =
        execute(
            "/api/profiles/$playerId/followees",
            HttpRequestMethod.GET
        ).deserializeJson(ZwiftFollowee.serializer().list)

    suspend fun getActivities(start: Int = 0, limit: Int = 10) =
        execute(
            "/api/profiles/$playerId/activities?start=$start&limit=$limit",
            HttpRequestMethod.GET
        ).deserializeJson(ZwiftActivity.serializer().list)

    suspend fun getLatestActivity() = getActivities(0, 1).getOrNull(0)

    suspend fun getGoals() =
        execute("/api/profiles/$playerId/goals", HttpRequestMethod.GET).deserializeJson(ZwiftGoal.serializer().list)

    suspend fun deleteGoal(goalId: Int) = execute("/api/profiles/$playerId/goals/$goalId", HttpRequestMethod.DELETE)

    suspend fun giveRideOn(playerId: Int, activityId: Long): ZwiftActivity {
        val body = jsonMap()
        body += json { "activityId" to activityId }
        body += json { "profileId" to this@ZwiftProfileApi.playerId }

        return execute(
            "/api/profiles/$playerId/activities/$activityId/rideon", HttpRequestMethod.POST,
            bodyString = body.toJson(), contentType = "application/json"
        ).deserializeJson(ZwiftActivity.serializer())
    }
}


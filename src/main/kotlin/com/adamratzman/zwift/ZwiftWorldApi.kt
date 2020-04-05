package com.adamratzman.zwift

import com.adamratzman.zwift.http.HttpHeader
import com.adamratzman.zwift.http.HttpRequestMethod
import com.adamratzman.zwift.http.ZwiftEndpoint
import com.adamratzman.zwift.models.ZwiftPlayerState
import com.adamratzman.zwift.models.ZwiftWorld
import com.adamratzman.zwift.models.ZwiftWorldEnum

class ZwiftWorldApi(client: ZwiftClient) : ZwiftEndpoint(client) {
    suspend fun getWorld(): ZwiftWorld {
        return execute("/relay/worlds/1", HttpRequestMethod.GET).deserializeJson(ZwiftWorld.serializer())
    }

    suspend fun getPlayerStatus(world: ZwiftWorldEnum, playerId: Int): ZwiftPlayerState {
        return executeByte(
            "/relay/worlds/${world.number}/players/$playerId", HttpRequestMethod.GET,
            additionalHeaders = listOf(HttpHeader("Accept-Type", "application/x-protobuf-lite"))
        ).deserializeProtobuf(ZwiftPlayerState.serializer())
    }
}
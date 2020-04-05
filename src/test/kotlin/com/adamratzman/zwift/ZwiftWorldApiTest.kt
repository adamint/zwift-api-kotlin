package com.adamratzman.zwift

import com.adamratzman.zwift.models.ZwiftPlayerState
import com.adamratzman.zwift.models.ZwiftWorldEnum
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ZwiftWorldApiTest {
    val api = client.worldApi

    @Test
    fun getPlayers() {
        runBlocking {
            val world = api.getWorld(ZwiftWorldEnum.WATOPIA)
            println(world.worldPlayers[0])

            println(world.worldPlayers.filter { it.firstName.startsWith("X") && it.lastName.startsWith("T") })

        }
    }

    @Test
    fun getPlayerStatus() {
        runBlocking {
            val playerState = api.getPlayerStatus(ZwiftWorldEnum.WATOPIA, api.getWorld(ZwiftWorldEnum.WATOPIA).worldPlayers[0].playerId)
            println(playerState)
        }
    }

}
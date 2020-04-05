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
                val world = api.getWorld()
                println(world.worldPlayers[0])

        }
    }

    @Test
    fun getPlayerStatus() {
        runBlocking {
            val playerState = api.getPlayerStatus(ZwiftWorldEnum.WATOPIA, api.getWorld().worldPlayers[0].playerId)
            println(playerState)
        }
    }

}
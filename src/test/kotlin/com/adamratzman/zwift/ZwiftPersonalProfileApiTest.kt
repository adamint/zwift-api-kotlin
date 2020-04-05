package com.adamratzman.zwift

import com.adamratzman.zwift.models.ZwiftWorldEnum
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ZwiftPersonalProfileApiTest {
    val api = client.profileApi

    @Test
    fun getProfile() {
        com.garmin.fit.AccelerometerDataMesgListener {  }
        println(api.playerId)
    }

    @Test
    fun getFollowers() {
        runBlocking {
            println(api.getFollowers())
        }
    }

    @Test
    fun getFollowees() {
        runBlocking {
            println(api.getFollowees())
        }
    }

    @Test
    fun getActivities() {
        runBlocking {
            println(api.getActivities())
        }
    }

    @Test
    fun getGoals() {
        runBlocking {
            println(api.getGoals())
        }
    }

    @Test
    fun getOtherProfile() {
        runBlocking {
            println(api.getOtherProfile(api.playerId))
        }
    }

    @Test
    fun giveRideOn() {
        runBlocking {
            println(api.playerId)
            val players = client.worldApi.getWorld()
            val player = players.worldPlayers[9]
            println(player)
            println(client.worldApi.getPlayerStatus(ZwiftWorldEnum.WATOPIA, player.playerId))
            println(client.worldApi.getPlayerStatus(ZwiftWorldEnum.WATOPIA, player.playerId).rideOns)
            val otherProfile = api.getOtherProfile(player.playerId)
            println(otherProfile)
            val rideOnResponse = api.giveRideOn(player.playerId, otherProfile.currentActivityId!!)
            println(rideOnResponse)
            println(client.worldApi.getPlayerStatus(ZwiftWorldEnum.WATOPIA, player.playerId))
            println(client.worldApi.getPlayerStatus(ZwiftWorldEnum.WATOPIA, player.playerId).rideOns)
        }
    }
}
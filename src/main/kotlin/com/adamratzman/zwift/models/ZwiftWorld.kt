package com.adamratzman.zwift.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class ZwiftWorldEnum(val number: Int) {
    WATOPIA(1),
    RICHMOND(2),
    LONDON(3),
    NEW_YORK(4),
    INNSBRUCK(5),
    YORKSHIRE(7),
    CRIT_CITY(8)
}

@Serializable
data class ZwiftWorld(
    val currentDateTime: Int,
    val currentWorldTime: Long,
    @SerialName("friendsInWorld") val worldPlayers: List<ZwiftWorldPlayer>,
    val name: String,
    val playerCount: Int,
    val worldId: Int
)

@Serializable
data class ZwiftWorldPlayer(
    val countryISOCode: Int,
    val currentSport: String,
    val enrolledZwiftAcademy: Boolean,
    val firstName: String,
    val followerStatusOfLoggedInPlayer: String,
    val ftp: Int,
    val lastName: String,
    val male: Boolean,
    @SerialName("mapId") val worldId: Int,
    val playerId: Int,
    val playerType: String,
    val rideDurationInSeconds: Int,
    val rideOnGiven: Boolean = false,
    val runTime10kmInSeconds: Int,
    val totalDistanceInMeters: Int
)

package com.adamratzman.zwift.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoId
import kotlinx.serialization.protobuf.ProtoNumberType
import kotlinx.serialization.protobuf.ProtoType

@Serializable
data class ZwiftPlayerState(
    @ProtoId(1) val id: Int,
    @ProtoId(2) val worldTime: Long,
    @ProtoId(3) val distance: Int,
    @ProtoId(4) val roadTime: Int,
    @ProtoId(5) val laps: Int,
    @ProtoId(6) val speed: Int,
    @ProtoId(8) val roadPosition: Int,
    @ProtoId(9) val cadenceUHz: Int,
    @ProtoId(11) val heartrate: Int,
    @ProtoId(12) val power: Int,
    @ProtoId(13) val heading: Long,
    @ProtoId(14) val lean: Int,
    @ProtoId(15) val climbing: Int,
    @ProtoId(16) val time: Int,
    @ProtoId(19) val f19: Int,
    @ProtoId(20) val f20: Int,
    @ProtoId(21) val progress: Int,
    @ProtoId(22) val customisationId: Long? = null,
    @ProtoId(23) val justWatching: Int,
    @ProtoId(24) val calories: Int,
    @ProtoId(25) val x: Float,
    @ProtoId(26) val altitude: Float,
    @ProtoId(27) val y: Float,
    @ProtoId(28) val watchingRiderId: Int,
    @ProtoId(29) val groupId: Int,
    @ProtoId(31) val sport: Long
) {
    val rideOns get() = (f19 shr 24) and 0xfff
    val isTurning get() = f19 and 8 != 0
    val isForward get() = (f19 and 4) != 0
    val course get() = f19 and 0xff0000 shr 16 // TODO figure out what this is
    val roadDirection get() = f20.toLong().and("ffff000000".toLong(16)) shr 24
    val turnSignal
        get() = when (f20 and 0x70) {
            0x10 -> TurnSignal.RIGHT
            0x20 -> TurnSignal.LEFT
            0x40 -> TurnSignal.STRAIGHT
            else -> null
        }
    val powerUp get() = f20 and 0xf
    val hasFeatherBoost get() = powerUp == 0
    val hasDraftBoost get() = powerUp == 1
    val hasAeroBoost get() = powerUp == 5
    val cadence get() = cadenceUHz.toDouble() / 1000000.0 * 60.0

}

enum class TurnSignal {
    RIGHT,
    LEFT,
    STRAIGHT
}

@Serializable
data class ZwiftOtherProfile(
    @ProtoId(1) val id: Int,
    @ProtoId(4) val firstName: String,
    @ProtoId(5) val lastName: String,
    @ProtoId(6) val male: Int,
    @ProtoId(9) val weight: Int,
    @ProtoId(12) val bodyType: Int,

    @ProtoId(34) val countryCode: Int,
    @ProtoId(35) val totalDistance: Int,
    @ProtoId(36) val totalDistanceClimbed: Int,
    @ProtoId(37) val totalTimeInMinutes: Int,
    @ProtoId(41) val totalWattHours: Int,
    @ProtoId(42) val height: Int,

    @ProtoId(46) val totalExperiencePoints: Int,
    @ProtoId(49) val achievementLevel: Int,
    @ProtoId(52) val powerSource: Int,
    @ProtoId(55) val age: Int,
    @ProtoId(108) val launchedGameClient: String,
    @ProtoId(109) val currentActivityId: Long? = null
) {
    val realPowerSource
        get() = when (powerSource) {
            0 -> ZwiftPowerSource.Z_POWER
            1 -> ZwiftPowerSource.POWER_METER
            2 -> ZwiftPowerSource.SMART_TRAINER
            else -> null
        }
}


enum class ZwiftPowerSource {
    Z_POWER,
    POWER_METER,
    SMART_TRAINER
}
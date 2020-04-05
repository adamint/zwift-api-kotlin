package com.adamratzman.zwift.models

import kotlinx.serialization.Serializable

@Serializable
data class ZwiftActivity(
    val activityCommentCount: Int,
    val activityRideOnCount: Int,
    val autoClosed: Boolean,
    val avgWatts: Double,
    val calories: Double,
    val description: String? = null,
    val distanceInMeters: Double,
    val duration: String, // hr:min
    val endDate: String?=null,
    val fitFileBucket: String?=null,
    val fitFileKey: String?=null,
    val id: Long,
    val id_str: String,
    val lastSaveDate: String?=null,//datetime
    val movingTimeInMs: Int?=null,
    val name: String,
    val primaryImageUrl: String?=null,
    val privateActivity: Boolean,
    val profile: ZwiftProfile,
    val profileId: Int,
    val rideOnGiven: Boolean,
    val sport: String,
    val startDate: String, //timestamp
    val totalElevation: Double,
    val worldId: Int,
    val avgHeartRate: Double? = null,
    val maxHeartRate: Double? = null,
    val maxWatts: Double? = null,
    val avgCadenceInRotationsPerMinute: Double? = null,
    val maxCadenceInRotationsPerMinute: Double? = null,
    val avgSpeedInMetersPerSecond: Double? = null,
    val maxSpeedInMetersPerSecond: Double? = null,
    val privacy: String? = null, // known: not_set
    val overridenFitnessPrivate: String? = null, // known: use_default
    val profileFtp: Double? = null,
    val profileMaxHeartRate: Double? = null
)
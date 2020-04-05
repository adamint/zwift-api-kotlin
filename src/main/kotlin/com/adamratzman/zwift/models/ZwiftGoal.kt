package com.adamratzman.zwift.models

import kotlinx.serialization.Serializable

@Serializable
data class ZwiftGoal(
    val actualDistanceInMeters: Double,
    val actualDurationInMinutes: Double,
    val createdOn: String, //datetime
    val id: Int,
    val name: String,
    val periodEndDate: String, //datetime
    val periodicity: Int,
    val profileId: Int,
    val sport: String, // CYCLING, RUNNING??
    val status: Int, // 0 not completed, 1 completed?
    val targetDistanceInMeters: Double,
    val targetDurationInMinutes: Double,
    val timezone: String,
    val type: Int
)
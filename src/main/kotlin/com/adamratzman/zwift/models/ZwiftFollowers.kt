package com.adamratzman.zwift.models

import kotlinx.serialization.Serializable

@Serializable
data class ZwiftFollowee(
    val followeeId: Int,
    val followeeProfile: ZwiftProfile,
    val followerId: Int,
    val id: Int,
    val isFolloweeFavoriteOfFollower: Boolean,
    val status: String
)

@Serializable
data class ZwiftFollower(
    val followeeId: Int,
    val followerProfile: ZwiftProfile,
    val followerId: Int,
    val id: Int,
    val isFolloweeFavoriteOfFollower: Boolean,
    val status: String
)

@Serializable
data class ZwiftProfile(
    val countryAlpha3: String,
    val countryCode: Int,
    val currentActivityId: Long?=null,
    val enrolledZwiftAcademy: Boolean,
    val firstName: String,
    val id: Int,
    val imageSrc: String?=null,
    val imageSrcLarge: String?=null,
    val lastName: String,
    val male: Boolean,
    val playerSubTypeId: Int?=null,
    val playerType: String,
    val playerTypeId: Int,
    val privacy: Privacy,
    val riding: Boolean,
    val socialFacts: SocialFacts?=null,
    val useMetric: Boolean,
    val worldId: Int?=null
)
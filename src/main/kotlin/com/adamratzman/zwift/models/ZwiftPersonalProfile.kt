package com.adamratzman.zwift.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ZwiftPersonalProfile(
    val achievementLevel: Int,
    val address: String?=null,
    val affiliate: String?=null,
    val age: Int,
    val avantlinkId: Int?=null,
    val b: Boolean,
    val bigCommerceId: Int?=null,
    val bodyType: Int,
    val bt: String,
    val connectedToFitbit: Boolean,
    val connectedToGarmin: Boolean,
    val connectedToStrava: Boolean,
    val connectedToTodaysPlan: Boolean,
    val connectedToTrainingPeaks: Boolean,
    val connectedToUnderArmour: Boolean,
    val connectedToWithings: Boolean,
    val countryAlpha3: String,
    val countryCode: Int,
    val createdOn: String,
    val currentActivityId: Long?=null,
    val cyclingOrganization: String?=null,
    val dob: String,
    val emailAddress: String,
    val enrolledZwiftAcademy: Boolean,
    val firstName: String,
    val ftp: Int,
    val fundraiserId: Int?=null,
    val height: Int,
    val id: Int,
    val imageSrc: String,
    val imageSrcLarge: String,
    val lastName: String,
    val launchedGameClient: String,
    val licenseNumber: Int?=null,
    val location: String,
    val male: Boolean,
    val mixpanelDistinctId: String,
    val numberOfFolloweesInCommon: Int,
    val origin: String?=null,
    val playerSubTypeId: Int?=null,
    val playerType: String,
    val playerTypeId: Int,
    val powerSourceModel: String,
    val powerSourceType: String,
    val preferredLanguage: String,
    val privacy: Privacy,
    val privateAttributes: Map<String,String>, // TODO
    val profileChanges: Boolean,
    val publicAttributes: PublicAttributes,
    val publicId: String,
    val riding: Boolean,
    val runAchievementLevel: Int,
    val runTime10kmInSeconds: Int,
    val runTime1miInSeconds: Int,
    val runTime5kmInSeconds: Int,
    val runTimeFullMarathonInSeconds: Int,
    val runTimeHalfMarathonInSeconds: Int,
    val socialFacts: SocialFacts,
    val source: String,
    val stravaPremium: Boolean,
    val totalDistance: Int,
    val totalDistanceClimbed: Int,
    val totalExperiencePoints: Int,
    val totalGold: Int,
    val totalInKomJersey: Int,
    val totalInOrangeJersey: Int,
    val totalInSprintersJersey: Int,
    val totalRunCalories: Int,
    val totalRunDistance: Int,
    val totalRunExperiencePoints: Int,
    val totalRunTimeInMinutes: Int,
    val totalTimeInMinutes: Int,
    val totalWattHours: Int,
    val useMetric: Boolean,
    val userAgent: String,
    val virtualBikeModel: String,
    val weight: Int,
    val worldId: Int?=null
)

@Serializable
data class SocialFacts(
    val followeeStatusOfLoggedInPlayer: String,
    val followeesCount: Int,
    val followeesInCommonWithLoggedInPlayer: Int,
    val followerStatusOfLoggedInPlayer: String,
    val followersCount: Int,
    val isFavoriteOfLoggedInPlayer: Boolean,
    val profileId: Int
)

@Serializable
data class Privacy(
    val approvalRequired: Boolean,
    val defaultActivityPrivacy: String,
    val defaultFitnessDataPrivacy: Boolean,
    val displayWeight: Boolean,
    val minor: Boolean,
    val privateMessaging: Boolean,
    val suppressFollowerNotification: Boolean
)

@Serializable
data class PublicAttributes(
    @SerialName("592008318") val trainingPlan: String?=null, // time, ms
    @SerialName("-1395504960") val trainingPlanName:String?=null, // time, ms
    @SerialName("-1815430206") val trainingPlanEnds:String?=null, // time, ms
    @SerialName("-689773580") val trainingPlanStarts:String? =null// time, ms
)


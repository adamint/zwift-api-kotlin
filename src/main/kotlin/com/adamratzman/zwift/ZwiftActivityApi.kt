package com.adamratzman.zwift

import com.adamratzman.zwift.http.HttpRequestMethod
import com.adamratzman.zwift.http.ZwiftEndpoint
import com.adamratzman.zwift.models.ZwiftActivity
import com.garmin.fit.Decode
import de.saring.exerciseviewer.data.EVExercise
import de.saring.exerciseviewer.parser.impl.garminfit.FitMessageListener
import kotlinx.serialization.builtins.list
import java.io.BufferedInputStream
import java.net.URL

class ZwiftActivityApi(client: ZwiftClient) : ZwiftEndpoint(client) {
    suspend fun getActivity(playerId: Int, activityId: Int) =
        execute(
            "/api/profiles/$playerId/activities/$activityId",
            HttpRequestMethod.GET
        ).deserializeJson(ZwiftActivity.serializer())

    suspend fun getActivities(start: Int = 0, limit: Int = 20) =
        execute(
            "/api/profiles/${client.profileApi.playerId}/activities?start=$start&limit=$limit",
            HttpRequestMethod.GET,
            contentType = "application/json"
        ).deserializeJson(ZwiftActivity.serializer().list)

    suspend fun getAllActivites(start: Int = 0, limit: Int = 20): List<ZwiftActivity> {
        val activities = mutableListOf<ZwiftActivity>()
        var currentStart = start
        do {
            val currActivites = getActivities(currentStart, limit)
            activities.addAll(currActivites)
            currentStart += currActivites.size
        } while (currActivites.isNotEmpty())

        return activities
    }

    fun getFitFileInputStream(fitFileBucket: String, fitFileId: String): BufferedInputStream {
        val url = URL("https://$fitFileBucket.s3.amazonaws.com/$fitFileId")
        val connection = url.openConnection()
        connection.connect()
        return BufferedInputStream(url.openStream())
    }

    fun getAndParseFitFileToExercise(fitFileBucket: String, fitFileId: String): EVExercise {
        val messageListener = FitMessageListener()
        getFitFileInputStream(fitFileBucket, fitFileId).use { Decode().read(it, messageListener) }
        return messageListener.getExercise()
    }
}
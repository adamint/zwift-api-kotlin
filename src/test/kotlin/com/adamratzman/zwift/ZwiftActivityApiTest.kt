package com.adamratzman.zwift

import com.adamratzman.zwift.models.ZwiftActivity
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.builtins.list
import org.junit.Assert
import org.junit.Test

class ZwiftActivityApiTest {
    val api = client.activityApi

    @Test
    fun testGetActivites() {
        runBlocking {
            println(api.getActivities())
            println(api.getActivities(0, 0))
        }
    }

    @Test
    fun testGetAllActivites() {
        runBlocking {
            val allActivities = api.getAllActivites()
            println(allActivities.size)
            println(json.stringify(ZwiftActivity.serializer().list, allActivities))
        }
    }

    @Test
    fun testFitDecode() {
        runBlocking {
            val exercise =
                api.getAndParseFitFileToExercise("s3-fit-prd-uswest2-zwift", "prod/491278/336d923f-519765789293261664")
            Assert.assertTrue(exercise.speed!!.speedAvg == 27.7236f)
            println(exercise.heartRateAVG)
            println(exercise.sampleList.take(10))
        }
    }
}
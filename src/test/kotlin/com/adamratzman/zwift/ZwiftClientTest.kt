package com.adamratzman.zwift

import org.junit.Test

val client = ZwiftClient(System.getenv("zwift_username"), System.getenv("zwift_password"))
val json = client.json
 class ZwiftClientTest {
    @Test
    fun testCreateClient() {
        println(client.authToken)
    }
}
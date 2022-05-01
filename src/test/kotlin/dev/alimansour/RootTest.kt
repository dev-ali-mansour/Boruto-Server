package dev.alimansour

import dev.alimansour.plugins.configureRouting
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class RootTest {

    @Test
    fun `access root endpoint, assert correct information`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Welcome to Boruto API!", bodyAsText())
        }
    }

    @Test
    fun `access non exist endpoint, assert not found`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/unknown").apply {
            assertEquals(HttpStatusCode.NotFound, status)
            assertEquals("Page not found", bodyAsText())
        }
    }
}
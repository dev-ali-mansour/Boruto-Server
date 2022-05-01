package dev.alimansour

import dev.alimansour.models.ApiResponse
import dev.alimansour.plugins.configureRouting
import dev.alimansour.repository.HeroRepository
import dev.alimansour.repository.HeroRepositoryImpl
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SearchHeroesTest {

    @Test
    fun `access search heroes endpoint, query hero name, assert single hero result`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/boruto/heroes/search?name=Sas").apply {
            val actual = Json.decodeFromString<ApiResponse>(bodyAsText()).heroes.size
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(expected = 1, actual = actual)
        }
    }

    @Test
    fun `access search heroes endpoint, query hero name, assert multiple heros result`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/boruto/heroes/search?name=Sa").apply {
            val actual = Json.decodeFromString<ApiResponse>(bodyAsText()).heroes.size
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(expected = 3, actual = actual)
        }
    }

    @Test
    fun `access search heroes endpoint, query empty text, assert empty list as a result`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/boruto/heroes/search?name=").apply {
            val actual = Json.decodeFromString<ApiResponse>(bodyAsText()).heroes
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(expected = emptyList(), actual = actual)
        }
    }

    @Test
    fun `access search heroes endpoint, query non existing hero, assert empty list as a result`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/boruto/heroes/search?name=unknown").apply {
            val actual = Json.decodeFromString<ApiResponse>(bodyAsText()).heroes
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(expected = emptyList(), actual = actual)
        }
    }

}
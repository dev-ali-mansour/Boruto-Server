package dev.alimansour

import dev.alimansour.models.ApiResponse
import dev.alimansour.models.Hero
import dev.alimansour.plugins.configureRouting
import dev.alimansour.repository.HeroRepository
import dev.alimansour.repository.HeroRepositoryImpl
import dev.alimansour.repository.NEXT_PAGE_KEY
import dev.alimansour.repository.PREVIOUS_PAGE_KEY
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AllHeroesTest {
    private lateinit var heroRepository: HeroRepository

    @BeforeTest
    fun setup() {
        heroRepository = HeroRepositoryImpl()
    }

    @Test
    fun `access all heroes endpoint, query all pages, assert correct information`() = testApplication {
        application {
            configureRouting()
        }
        val limit = 4
        val allHeroes = heroRepository.heroes.windowed(
            size = limit,
            step = limit,
            partialWindows = true
        )
        val pages = 1..allHeroes.size
        pages.forEach {
            println("Page: $it")
        }
        pages.forEach { page ->
            println("Page: $page")
            client.get("/boruto/heroes?page=$page&limit=$limit").apply {
                val expected = ApiResponse(
                    success = true,
                    message = "Ok",
                    prevPage = calculatePage(
                        heroes = heroRepository.heroes,
                        page = page,
                        limit = limit
                    )[PREVIOUS_PAGE_KEY],
                    nextPage = calculatePage(
                        heroes = heroRepository.heroes,
                        page = page,
                        limit = limit
                    )[NEXT_PAGE_KEY],
                    heroes = provideHeroes(
                        heroes = heroRepository.heroes,
                        page = page,
                        limit = limit
                    ),
                )
                val actual = Json.decodeFromString<ApiResponse>(bodyAsText())
                expected.lastUpdated = actual.lastUpdated

                assertEquals(HttpStatusCode.OK, status)
                assertEquals(expected = expected, actual = actual)
            }
        }
    }

    @Test
    fun `access all heroes endpoint, query non existing page number, assert error`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/boruto/heroes?page=6").apply {
            val expected = ApiResponse(
                success = false,
                message = "Heroes not found!",
            )
            val actual = Json.decodeFromString<ApiResponse>(bodyAsText())

            assertEquals(HttpStatusCode.BadRequest, status)
            assertEquals(expected = expected, actual = actual)
        }
    }

    @Test
    fun `access all heroes endpoint, query invalid page number, assert error`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/boruto/heroes?page=invalid").apply {
            val expected = ApiResponse(
                success = false,
                message = "Only numbers allowed for page!"
            )
            val actual = Json.decodeFromString<ApiResponse>(bodyAsText())

            assertEquals(HttpStatusCode.BadRequest, status)
            assertEquals(expected = expected, actual = actual)
        }
    }

    private fun calculatePage(
        heroes: List<Hero>,
        page: Int,
        limit: Int
    ): Map<String, Int?> {
        val allHeroes = heroes.windowed(
            size = limit,
            step = limit,
            partialWindows = true
        )
        require(page <= allHeroes.size)
        val prevPage = if (page == 1) null else page - 1
        val nextPage = if (page == allHeroes.size) null else page + 1
        return mapOf(PREVIOUS_PAGE_KEY to prevPage, NEXT_PAGE_KEY to nextPage)
    }

    private fun provideHeroes(
        heroes: List<Hero>,
        page: Int,
        limit: Int
    ): List<Hero> {
        val allHeroes = heroes.windowed(
            size = limit,
            step = limit,
            partialWindows = true
        )
        require(page > 0 && page <= allHeroes.size)
        return allHeroes[page - 1]
    }
}
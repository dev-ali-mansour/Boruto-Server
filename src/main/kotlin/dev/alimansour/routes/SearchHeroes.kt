package dev.alimansour.routes

import dev.alimansour.repository.HeroRepository
import dev.alimansour.util.inject
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.searchHeroes() {
    val heroRepository: HeroRepository by inject()
    get(path = "/boruto/heroes/search") {
        val name = call.request.queryParameters["name"]
        val apiResponse = heroRepository.searchHeroes(name = name   )
        call.respond(
            message = apiResponse,
            status = HttpStatusCode.OK
        )
    }
}
package dev.alimansour.routes

import dev.alimansour.models.ApiResponse
import dev.alimansour.repository.HeroRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllHeroes(heroRepository: HeroRepository) {
    get(path = "/boruto/heroes") {
        runCatching {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            val limit = call.request.queryParameters["limit"]?.toInt() ?: 4

            val apiResponse = heroRepository.getAllHeroes(
                page = page,
                limit = limit
            )

            call.respond(
                message = apiResponse,
                status = HttpStatusCode.OK
            )
        }.onFailure { t ->
            when (t) {
                is java.lang.NumberFormatException -> {
                    call.respond(
                        message = ApiResponse(success = false, message = "Only numbers allowed for page!"),
                        status = HttpStatusCode.BadRequest
                    )
                }
                is java.lang.IllegalArgumentException -> {
                    call.respond(
                        message = ApiResponse(success = false, message = "Heroes not found!"),
                        status = HttpStatusCode.BadRequest
                    )
                }
            }
        }
    }
}
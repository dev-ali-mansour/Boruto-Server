package dev.alimansour.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import javax.naming.AuthenticationException

fun Application.configureStatusPages() {
    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, status ->
            call.respond(
                message = "Page not found",
                status = status
            )
        }
        exception<Throwable> { call, cause ->
            when (cause) {
                is AuthenticationException -> {
                    call.respond(
                        message = "You don't have permission to access this page!",
                        status = HttpStatusCode.Unauthorized
                    )
                }
                else -> {
                    call.respond(
                        message = "Oops! We caught an error: ${cause.message}",
                        status = HttpStatusCode.BadRequest
                    )
                }
            }
        }
    }
}
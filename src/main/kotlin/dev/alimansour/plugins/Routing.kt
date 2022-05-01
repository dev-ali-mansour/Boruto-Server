package dev.alimansour.plugins

import dev.alimansour.routes.getAllHeroes
import dev.alimansour.routes.root
import dev.alimansour.routes.searchHeroes
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import javax.naming.AuthenticationException

fun Application.configureRouting() {

    routing {
        root()
        getAllHeroes()
        searchHeroes()

        get(path = "test2"){
            throw AuthenticationException()
        }

        static (remotePath = "/images"){
            resources("images")
        }
    }
}

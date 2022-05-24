package dev.alimansour.plugins

import dev.alimansour.repository.HeroRepository
import dev.alimansour.routes.getAllHeroes
import dev.alimansour.routes.root
import dev.alimansour.routes.searchHeroes
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject
import javax.naming.AuthenticationException

fun Application.configureRouting() {

    routing {
        val heroRepository: HeroRepository by inject(HeroRepository::class.java)

        root()
        getAllHeroes(heroRepository = heroRepository)
        searchHeroes(heroRepository = heroRepository)

        get(path = "test2") {
            throw AuthenticationException()
        }

        static(remotePath = "/images") {
            resources("images")
        }
    }
}

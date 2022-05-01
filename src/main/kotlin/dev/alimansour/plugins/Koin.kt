package dev.alimansour.plugins

import dev.alimansour.di.koinModule
import dev.alimansour.util.Koin
import io.ktor.server.application.*


fun Application.configureKoin() {
    install(Koin) {
         //slf4jLogger(level = org.koin.core.logger.Level.ERROR)
         modules.add(koinModule)
    }
}
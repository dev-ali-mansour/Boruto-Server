package dev.alimansour.plugins

import dev.alimansour.di.koinModule
import io.ktor.server.application.*
import io.ktor.util.*
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun Application.configureKoin() {
    install(CustomKoinPlugin) {
        modules(koinModule)
    }
}

internal class CustomKoinPlugin {
    // Implements ApplicationPlugin as a companion object.
    companion object Plugin : BaseApplicationPlugin<ApplicationCallPipeline, KoinApplication, CustomKoinPlugin> {
        // Creates a unique key for the plugin.
        override val key = AttributeKey<CustomKoinPlugin>("CustomKoinPlugin")

        // Code to execute when installing the plugin.
        override fun install(
            pipeline: ApplicationCallPipeline,
            configure: KoinApplication.() -> Unit
        ): CustomKoinPlugin {
            startKoin(appDeclaration = configure)
            return CustomKoinPlugin()
        }
    }
}
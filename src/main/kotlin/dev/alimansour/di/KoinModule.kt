package dev.alimansour.di

import dev.alimansour.repository.HeroRepository
import dev.alimansour.repository.HeroRepositoryImpl
import org.koin.dsl.module

val koinModule = module {
    single<HeroRepository> {
        HeroRepositoryImpl()
    }
}
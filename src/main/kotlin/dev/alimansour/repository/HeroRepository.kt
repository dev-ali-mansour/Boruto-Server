package dev.alimansour.repository

import dev.alimansour.models.ApiResponse
import dev.alimansour.models.Hero

interface HeroRepository {

    val heroes: List<Hero>

    suspend fun getAllHeroes(page: Int = 1, limit: Int = 4): ApiResponse
    suspend fun searchHeroes(name: String?): ApiResponse

}
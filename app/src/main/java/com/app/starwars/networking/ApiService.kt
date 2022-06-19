package com.app.starwars.networking

import com.app.starwars.models.PlanetResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("?format=json")
    suspend fun fetchPlanets(
        @Query("page") page: Int = 0
    ): PlanetResponse
}

package com.jovita.startwarplanets.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("planets/")
    suspend fun getPlanetList(): Response<PlanetData>

    @GET("planets/{planet_id}")
    suspend fun getPlanetDetails(@Path(value = "planet_id") planetId : String): Response<Planet>

}
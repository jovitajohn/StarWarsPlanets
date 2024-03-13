package com.jovita.startwarplanets.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("planets/")
    suspend fun getPlanetList(): Response<PlanetData>

    @GET("planets/1")
    suspend fun getPlanetDetails(): Response<Planet>
}
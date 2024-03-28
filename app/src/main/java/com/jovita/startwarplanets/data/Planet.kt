package com.jovita.startwarplanets.data

import com.google.gson.annotations.SerializedName

data class Planet(
    val message : String = " ",
    val result : PlanetResult? = null
)

data class PlanetResult(
    val properties : PlanetProperties,
    val description : String,
    @SerializedName("_id")
    val id : String,
    val uid : String,
    @SerializedName("__v")
    val version : Int
)

data class PlanetProperties(
    val diameter: String,
    val rotation_period: String,
    val orbital_period: String,
    val gravity: String,
    val population: String,
    val climate: String,
    val terrain: String,
    val surface_water: String,
    val created: String,
    val edited: String,
    val name: String,
    val url: String
)


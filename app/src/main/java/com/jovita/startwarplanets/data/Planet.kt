package com.jovita.startwarplanets.data

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class Planet( val name: String,
                   @JsonProperty("rotation_period")
                   val rotationPeriod: String,
                   @JsonProperty("orbital_period")
                   val orbitalPeriod: String,
                   val diameter: String,
                   val climate: String,
                   val gravity: String,
                   val terrain: String,
                   @JsonProperty("surface_water")
                   val surfaceWater: String,
                   val population: String,
                   val residents: List<String>,
                   val films: List<String>,
                   val created: String,
                   val edited: String,
                   val url: String,) : Serializable


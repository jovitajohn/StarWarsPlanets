package com.jovita.startwarplanets.data

import com.fasterxml.jackson.annotation.JsonProperty

data class PlanetData (   val count: Long,
                          val next: String,
                          val previous: Any?,
                          val results: List<Planet>,)
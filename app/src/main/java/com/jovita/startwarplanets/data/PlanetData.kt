package com.jovita.startwarplanets.data

import java.io.Serializable

data class PlanetData(
    val message: String,
    val total_records: Int,
    val total_pages: Int,
    val next: String,
    val previous: String,
    val results: List<RootPlanetItem>,
)

data class RootPlanetItem(
    val uid: String,
    val name: String,
    val url: String
) : Serializable
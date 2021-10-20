package com.carkzis.pomona.data.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkFruit(
    val type: String,
    val price: String,
    val weight: String
)

@JsonClass(generateAdapter = true)
data class FruitContainer(
    val fruit: String
)
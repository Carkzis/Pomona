package com.carkzis.pomona

import androidx.room.Entity
import androidx.room.PrimaryKey

data class DomainFruit(
    val type: String,
    val price: String,
    val weight: String
)

data class NetworkFruit(
    val type: String,
    val price: String,
    val weight: String
)

@Entity
data class DatabaseFruit(
    @PrimaryKey
    val type: String,
    val price: String,
    val weight: String
)
package com.carkzis.pomona

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DatabaseFruit(
    @PrimaryKey
    val type: String,
    val price: String,
    val weight: String
)
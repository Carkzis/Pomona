package com.carkzis.pomona

import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun refreshFruitData()
    suspend fun getFruit() : Flow<List<DatabaseFruit>>
}
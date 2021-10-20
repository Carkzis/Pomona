package com.carkzis.pomona.data

import com.carkzis.pomona.data.local.DatabaseFruit
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun refreshFruitData()
    suspend fun getFruit() : Flow<List<DatabaseFruit>>
}
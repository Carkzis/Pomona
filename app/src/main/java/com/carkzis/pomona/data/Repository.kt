package com.carkzis.pomona.data

import com.carkzis.pomona.data.local.DatabaseFruit
import com.carkzis.pomona.util.LoadingState
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun refreshFruitData(): Flow<LoadingState>
    suspend fun getFruit() : Flow<List<DatabaseFruit>>
}
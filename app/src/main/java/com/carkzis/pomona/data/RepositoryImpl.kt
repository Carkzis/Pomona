package com.carkzis.pomona.data

import com.carkzis.pomona.data.local.DatabaseFruit
import com.carkzis.pomona.data.local.PomonaDatabase
import com.carkzis.pomona.util.LoadingState
import kotlinx.coroutines.flow.Flow

class RepositoryImpl (private val database: PomonaDatabase): Repository {
    override suspend fun refreshFruitData(): Flow<LoadingState> {
        TODO("Not yet implemented")
    }

    override suspend fun getFruit(): Flow<List<DatabaseFruit>> {
        TODO("Not yet implemented")
    }
}
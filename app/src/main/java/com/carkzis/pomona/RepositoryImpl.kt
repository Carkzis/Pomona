package com.carkzis.pomona

import kotlinx.coroutines.flow.Flow

class RepositoryImpl (private val database: PomonaDatabase): Repository {
    override suspend fun refreshFruitData() {
        TODO("Not yet implemented")
    }

    override suspend fun getFruit(): Flow<List<DatabaseFruit>> {
        TODO("Not yet implemented")
    }
}
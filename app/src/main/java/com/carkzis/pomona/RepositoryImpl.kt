package com.carkzis.pomona

import kotlinx.coroutines.flow.Flow

class RepositoryImpl : Repository {
    override suspend fun refreshFruitData() {
        TODO("Not yet implemented")
    }

    override suspend fun getFruit(): Flow<List<FruitDB>> {
        TODO("Not yet implemented")
    }
}
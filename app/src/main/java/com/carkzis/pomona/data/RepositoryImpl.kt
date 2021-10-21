package com.carkzis.pomona.data

import com.carkzis.pomona.R
import com.carkzis.pomona.data.local.DatabaseFruit
import com.carkzis.pomona.data.local.PomonaDatabase
import com.carkzis.pomona.data.remote.FruitApi
import com.carkzis.pomona.data.remote.asDatabaseModel
import com.carkzis.pomona.util.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RepositoryImpl (private val database: PomonaDatabase): Repository {

    override suspend fun refreshFruitData() = flow<LoadingState> {
        // Emit a loading state.
        emit(LoadingState.Loading(R.string.loading))

        // Perform a call the the Fruit API.
        val fruitList = FruitApi.retroService.getFruitInformation()

        // Insert the result into the database as a List of DatabaseFruit.
        database.fruitDao().insertAllFruit(fruitList.asDatabaseModel())
    }.catch {
        emit(LoadingState.Error(R.string.error, Exception()))
    }.flowOn(Dispatchers.IO)

    /**
     * Retrieves a list of fruit data from Room.
     */
    override suspend fun getFruit() = database.fruitDao().getAllFruit()
}
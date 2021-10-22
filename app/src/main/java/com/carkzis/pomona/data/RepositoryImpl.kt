package com.carkzis.pomona.data

import android.accounts.NetworkErrorException
import com.carkzis.pomona.R
import com.carkzis.pomona.data.local.DatabaseFruit
import com.carkzis.pomona.data.local.PomonaDatabase
import com.carkzis.pomona.data.local.asDomainModel
import com.carkzis.pomona.data.remote.FruitApi
import com.carkzis.pomona.data.remote.asDatabaseModel
import com.carkzis.pomona.util.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.io.IOException
import java.net.ConnectException

class RepositoryImpl (private val database: PomonaDatabase): Repository {

    /**
     * Performs a network call to the Fruit API, and refreshes the local SQLite database
     * with the result using Room.
     */
    override suspend fun refreshFruitData() = flow {
        // Emit a loading state.
        emit(LoadingState.Loading(R.string.loading))

        // Perform a call the the Fruit API.
        val fruitList = FruitApi.retroService.getFruitInformation()

        // Insert the result into the database as a List of DatabaseFruit.
        database.fruitDao().insertAllFruit(fruitList.asDatabaseModel())

        emit(LoadingState.Success(R.string.success, fruitList.fruit.size))
    }.catch {
        emit(LoadingState.Error(R.string.error, IOException()))
    }.flowOn(Dispatchers.IO)

    /**
     * Retrieves a list of fruit data from Room, mapping them to DomainFruit objects.
     */
    override fun getFruit() = database.fruitDao().getAllFruit().flatMapLatest {
        flow {
            emit(it.asDomainModel())
        }
    }
}
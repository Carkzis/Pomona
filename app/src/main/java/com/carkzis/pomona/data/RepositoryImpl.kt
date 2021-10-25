package com.carkzis.pomona.data

import com.carkzis.pomona.R
import com.carkzis.pomona.data.local.PomonaDatabase
import com.carkzis.pomona.data.local.asDomainModel
import com.carkzis.pomona.data.remote.FruitApi
import com.carkzis.pomona.data.remote.FruitContainer
import com.carkzis.pomona.data.remote.asDatabaseModel
import com.carkzis.pomona.stats.UsageStatsManager
import com.carkzis.pomona.util.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import kotlin.system.measureTimeMillis

/**
 * Repository for retrieving and storing fruit information.
 */
class RepositoryImpl (private val database: PomonaDatabase): Repository {

    /**
     * Performs a network call to the Fruit API, and refreshes the local SQLite database
     * with the result using Room.
     */
    override suspend fun refreshFruitData() = flow {
        // Emit a loading state.
        emit(LoadingState.Loading(R.string.loading))

        var fruitList: FruitContainer

        // This measures the time taken for the service call to be completed.
        val timeTaken = measureTimeMillis {
            // Perform a call the the Fruit API.
            fruitList = FruitApi.retroService.getFruitInformation()
        }

        // Insert the result into the database as a List of DatabaseFruit.
        database.fruitDao().insertAllFruit(fruitList.asDatabaseModel())

        // Emit the successful result.
        emit(LoadingState.Success(R.string.success, fruitList.fruit.size))

        // Generate service call completion time stats.
        UsageStatsManager.generateLoadEventStats(timeTaken)

    }.catch {
        /*
         Create an exception in order to generate error stats and emit the exception
         and error message.
         */
        val exception = IOException()
        UsageStatsManager.generateErrorEventStats(exception)
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
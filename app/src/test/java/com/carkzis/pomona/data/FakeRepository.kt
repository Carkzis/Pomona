package com.carkzis.pomona.data

import com.carkzis.pomona.R
import com.carkzis.pomona.data.local.DatabaseFruit
import com.carkzis.pomona.util.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FakeRepository @Inject constructor() : Repository {

    private val testDatabaseFruit = mutableListOf(
        DatabaseFruit("apple", 1000, 5000),
        DatabaseFruit("pear", 2000, 8000)
    )

    // This decides if the request is loading or not.
    var loading = false
    // This decides if the request succeeds or not.
    var failure = false

    override suspend fun refreshFruitData() = flow<LoadingState> {
        // Emit a loading state.
        emit(LoadingState.Loading(R.string.loading))
        // If we are only testing loading, skip the following code.
        if (!loading) {
            // If the network call is successful, emit a string resource confirming this.
            if (!failure) {
                emit(LoadingState.Success(R.string.success, testDatabaseFruit.size))
            } else {
                throw Exception()
            }
        }
    }.catch {
        emit(LoadingState.Error(R.string.error, Exception()))
    }.flowOn(Dispatchers.IO)

    override suspend fun getFruit() = flow<List<DatabaseFruit>> {
        emit(testDatabaseFruit)
    }

}
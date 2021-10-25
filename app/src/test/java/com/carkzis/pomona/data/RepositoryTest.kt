package com.carkzis.pomona.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.carkzis.pomona.util.LoadingState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RepositoryTest {

    // Execute a task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // FakeRepository is used for testing.
    private lateinit var repository: FakeRepository

    @Before
    fun setUp() {
        repository = FakeRepository()
    }

    @Test
    fun getFruit_retrieveTestFruit() = runBlockingTest {
        // Confirm that getFruit() emits the list of DatabaseFruit held locally.
        repository.getFruit().collect {
            assertThat(it[0].type, `is`("Apple"))
            assertThat(it[0].price, `is`("£10.00"))
            assertThat(it[0].weight, `is`("5.0kg"))
            assertThat(it[1].type, `is`("Pear"))
            assertThat(it[1].price, `is`("£20.00"))
            assertThat(it[1].weight, `is`("8.0kg"))
        }
    }

    @Test
    fun refreshFruitData_networkCallError_receiveLoadingStateError() = runBlocking {
        // Given that the network call will fail.
        repository.failure = true

        // Test the response, if we get LoadingState.Error, isSuccessful with be false.
        var isSuccessful = true
        repository.refreshFruitData().collect {
            isSuccessful = when (it) {
                is LoadingState.Error -> false
                is LoadingState.Success -> true
                else -> false
            }
        }

        // Assert that the refresh was not successful.
        assertThat(isSuccessful, `is`(false))
    }

    @Test
    fun refreshFruitData_networkCallSuccessful_receiveLoadingStateSuccess() = runBlocking {
        // Given that the network call will succeed.
        repository.failure = false

        // Test the response, if we get LoadingState.Success, isSuccessful with be true.
        var isSuccessful = false
        var listSize = 0 // Amount of results received from the refresh.
        repository.refreshFruitData().collect {
            isSuccessful = when (it) {
                is LoadingState.Error -> false
                is LoadingState.Success -> {
                    listSize = it.dataSize
                    true
                }
                else -> false
            }
        }

        // Assert that the refresh was successful, and we get the 2 DatabaseFruit items in a List.
        assertThat(isSuccessful, `is`(true))
        assertThat(listSize, `is`(2))
    }

    @Test
    fun refreshFruitData_networkCallInProgress_receiveLoadingStateLoading() = runBlocking {
        // Given that the network call will stay at loading.
        repository.loading = true

        // Test the response, if we get LoadingState.Loading, isLoading will be true.
        var isLoading = false
        repository.refreshFruitData().collect {
            isLoading = when (it) {
                is LoadingState.Error -> false
                is LoadingState.Success -> false
                is LoadingState.Loading -> true
            }
            }

        // Assert that the confirmation that the network call is in progress is emitted.
        assertThat(isLoading, `is`(true))
    }
}
package com.carkzis.pomona.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.carkzis.pomona.R
import com.carkzis.pomona.data.FakeRepository
import com.carkzis.pomona.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FruitListViewModelTest {

    private lateinit var fruitListViewModel: FruitListViewModel

    private lateinit var repository: FakeRepository

    // This is from the kotlin docs, to allow access to Dispatcher.Main in testing.
    private val dispatcher = TestCoroutineDispatcher()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = FakeRepository()
        fruitListViewModel = FruitListViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun refreshRepository_refreshedOnInitialisation_allDataCollectedIntoFruitListLiveData() = runBlocking {
        // refreshRepository is called on initialisation of FruitListViewModel.
        assertThat(fruitListViewModel.fruitList.getOrAwaitValue()?.size, `is`(2))
        assertThat(fruitListViewModel.fruitList.getOrAwaitValue()?.get(0)?.type, `is`("Apple"))
        assertThat(fruitListViewModel.fruitList.getOrAwaitValue()?.get(1)?.type, `is`("Pear"))
    }

    @Test
    fun refreshRepository_manualRefreshFailure_postFailureMessageToToastLiveData() = runBlocking {
        // Given that the network call will fail.
        repository.failure = true

        // Call the refresh method.
        fruitListViewModel.refreshRepository()

        // Delay, as a refreshRepository would be also be called on initiation.
        delay(100)

        // Assert that an error message is collected and added to toastText.
        assertThat(fruitListViewModel.toastText.getOrAwaitValue().getContextIfNotHandled(),
            `is`(R.string.error))
    }

    @Test
    fun refreshRepository_manualRefreshSuccess_postSuccessMessageToToastLiveData() = runBlocking {
        // Given that the network call will fail.
        repository.failure = false

        // Call the refresh method.
        fruitListViewModel.refreshRepository()

        // Delay, as a refreshRepository would be also be called on initiation.
        delay(100)

        // Assert that an success message is collected and added to toastText.
        assertThat(fruitListViewModel.toastText.getOrAwaitValue().getContextIfNotHandled(),
            `is`(R.string.success))
    }

}
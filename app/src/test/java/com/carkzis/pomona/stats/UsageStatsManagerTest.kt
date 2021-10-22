package com.carkzis.pomona.stats

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.carkzis.pomona.data.FakeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep

@ExperimentalCoroutinesApi
class UsageStatsManagerTest {

    // Execute a task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // This is from the kotlin docs, to allow access to Dispatcher.Main in testing.
    private val dispatcher = TestCoroutineDispatcher()

    // FakeRepository is used for testing.
    private var usageStatsManager = FakeUsageStatsManager()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        usageStatsManager = FakeUsageStatsManager()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun generateLoadEventStats_validValue_getPairOfEventAndTime() = runBlocking {
        // Given valid positive Integer.
        val milliSeconds = 5000

        // When the method is called.
        usageStatsManager.generateLoadEventStats(milliSeconds)

        // Assert that event stats are "sent" to the queryPair. This imitates sending usage stats.
        assertThat(usageStatsManager.queryPair.first, `is`("load"))
        assertThat(usageStatsManager.queryPair.second, `is`("5000"))
    }

    @Test
    fun generateLoadEventStats_invalidValue_noValuesAddedToPair() = runBlocking {
        // Given invalid negative Integer.
        val milliSeconds = -5000

        // When the method is called.
        usageStatsManager.generateLoadEventStats(milliSeconds)

        // Assert that event stats are "sent" to queryPair. This imitates not sending usage stats.
        assertThat(usageStatsManager.queryPair.first, `is`(null))
        assertThat(usageStatsManager.queryPair.second, `is`(null))
    }

    fun generateDisplayEventStats() {
    }

    fun generateErrorEventStats() {
    }

}
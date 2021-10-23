package com.carkzis.pomona.stats

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.carkzis.pomona.data.FakeRepository
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import java.lang.NullPointerException
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

        // Assert that event stats not "sent" to queryPair. This imitates not sending usage stats.
        assertThat(usageStatsManager.queryPair.first, `is`(nullValue()))
        assertThat(usageStatsManager.queryPair.second, `is`(nullValue()))
    }

    @Test
    fun generateDisplayEventStats_startAndStop_getPairOfEventAndPositiveTime() {
        // Call the method twice.
        usageStatsManager.generateDisplayEventStats()
        sleep(100) // Sleep so that we can generate a positive number on second method call.
        usageStatsManager.generateDisplayEventStats()

        // Assert that event stats "sent" to queryPair, and the milliseconds are positive.
        assertThat(usageStatsManager.queryPair.first, `is`("display"))
        assertTrue(usageStatsManager.queryPair.second!! > 0.toString())
    }

    @Test
    fun generateDisplayEventStats_startButNoStop_noValuesAddedToPair() {
        // Call the method once, which will not end up with the stats being sent.
        usageStatsManager.generateDisplayEventStats()
        sleep(100) // Sleep as a control.

        // Assert that event stats not "sent" to queryPair. This imitates not sending usage stats.
        assertThat(usageStatsManager.queryPair.first, `is`(nullValue()))
        assertThat(usageStatsManager.queryPair.second, `is`(nullValue()))
    }

    @Test
    fun generateErrorEventStats_exceptionInfoSent_getPairOfEventAndErrorReason() {
        // Given a null pointer exception.
        val exception = NullPointerException()

        // When we call the method to "send" the error report.
        usageStatsManager.generateErrorEventStats(exception)

        // Assert that event stats are sent to queryPair. This imitates sending the stats.
        assertThat(usageStatsManager.queryPair.first, `is`("error"))
        assertThat(
            usageStatsManager.queryPair.second, `is`(
                "null pointer exception on line ${exception.stackTrace[0].lineNumber} in usagestatsmanagertest"
            )
        )
    }

    @Test
    fun formatExceptionName_IOException_exceptionCorrectlyFormatted() {
        // Given a null pointer exception.
        val exception = IOException(); usageStatsManager.generateErrorEventStats(exception)

        // When we call the method to format the exception.
        val formattedException = usageStatsManager.formatExceptionName(exception)

        /*
        Assert that we get a correctly formatted exception, separated with spaces at capital
        and converted to lowercase.
         */
        assertThat(formattedException, `is`("i o exception"))
    }

    @Test
    fun formatClassName_blankStringEntered_unknownStringReturned() {
        // Given a blank String representing a full class name.
        val string = ""

        // When we call the method to format the class.
        val formattedClass = usageStatsManager.formatClassName(string)

        // Assert that we return "unknown" as our default value.
        assertThat(formattedClass, `is`("unknown"))
    }

    @Test
    fun formatClassName_fullClassStringEntered_simpleClassStringReturned() {
        // Given a blank String representing a full class name.
        val string = "com.carkzis.pomona.stats.UsageStatsManagerTest"

        // When we call the method to format the class.
        val formattedClass = usageStatsManager.formatClassName(string)

        // Assert that we return "unknown" as our default value.
        assertThat(formattedClass, `is`("usagestatsmanagertest"))
    }
}
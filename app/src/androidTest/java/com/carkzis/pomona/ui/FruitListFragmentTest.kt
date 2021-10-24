package com.carkzis.pomona.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.carkzis.pomona.DataBindingIdlingResource
import com.carkzis.pomona.R
import com.carkzis.pomona.data.Repository
import com.carkzis.pomona.monitorActivity
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions
import com.schibsted.spain.barista.interaction.BaristaSleepInteractions
import com.schibsted.spain.barista.rule.cleardata.ClearDatabaseRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class FruitListFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // This will clear the database before each test.
    @get:Rule
    var clearDatabaseRule = ClearDatabaseRule()

    @Inject
    lateinit var repository: Repository

    private val dataBindingIdlingResource = DataBindingIdlingResource()
    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun init() {
        hiltRule.inject()
    }

    @After
    fun closeActivityScenerio() = runBlockingTest {
        activityScenario.close()
    }

    /**
     * This assumes a working internet connection, and a successful result.
     * Can be replaced by a local dummy database to ensure we get the results we would expect.
     */
    @Test
    fun fruitListFragment_whenResultsSuccessful_displayFruitItemsOnUI() {
        // Using the FruitListFragment screen, which is the first displayed.
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        // The progress bar should show.
        onView(withId(R.id.fruit_list_progress_bar))
            .check(matches(isDisplayed()))

        // Introduce an artificial delay for the results using Barista.
        BaristaSleepInteractions.sleep(2000)

        // Assert that the "Apple" and "Orange" list items are displayed.
        onView(ViewMatchers.withText("Apple"))
            .check(matches(isDisplayed()))
        onView(ViewMatchers.withText("Orange"))
            .check(matches(isDisplayed()))

        // Make sure that the progress bar finishes and is no longer displayed.
        BaristaVisibilityAssertions.assertNotDisplayed(R.id.fruit_list_progress_bar)
    }

}
package com.carkzis.pomona

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.carkzis.pomona.ui.MainActivity
import com.carkzis.pomona.ui.list.FruitListFragment
import com.carkzis.pomona.ui.list.FruitListFragmentDirections
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class AppNavigationTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    /*
    Note: these tests assume that fruit data is held in the database, could use dummy
    data on an emulated database to ensure the data we want is available.
     */

    @Test
    fun fruitDetailFragment_backButton() = runBlockingTest {
        // On the contents screen
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        // Click a listed fruit.
        onView(withText("Apple"))
            .perform(click())

        // Click the back button.
        pressBack()

        // Confirm we end up at the FruitList, by checking that a different fruit is displayed.
        onView(withText("Banana")).check(matches(isDisplayed()))
    }

    @Test
    fun fruitListFragment_clickOnFruitItem_navigateToFruitDetailFragment() {
        // On the FruitListFragment screen.
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<FruitListFragment>(Bundle()) {
            navController.setGraph(R.navigation.navigation)
            Navigation.setViewNavController(requireView(), navController)
        }

        // Click a listed fruit.
        onView(withText("Apple"))
            .perform(click())

        // Verify that we navigate to the FruitDetail screen.
        verify(navController).navigate(
            FruitListFragmentDirections.actionFruitListFragmentToFruitDetailFragment(
                Mockito.any<Array<String>>()
            )
        )
    }


}
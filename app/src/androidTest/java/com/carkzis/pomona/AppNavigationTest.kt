package com.carkzis.pomona

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.carkzis.pomona.ui.list.FruitListFragment
import com.carkzis.pomona.ui.list.FruitListFragmentDirections
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    @Test
    fun fruitListFragment_clickOnFruitItem_navigateToFruitDetailFragment() {
        // On the FruitListFragment screen.
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<FruitListFragment>(Bundle()) {
            navController.setGraph(R.navigation.navigation)
            Navigation.setViewNavController(requireView(), navController)
        }

        // Click a listed fruit.
        onView(withId(R.id.clayout_fruit_item))
            .perform(click())

        // Verify that we navigate to the FruitDetail screen.
        verify(navController).navigate(
            FruitListFragmentDirections.actionFruitListFragmentToFruitDetailFragment(
                arrayOf(
                    "Ugly Fruit",
                    "£60.00",
                    "0.9kg"
                )
            )
        )
    }

}
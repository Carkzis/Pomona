package com.carkzis.pomona.ui

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.carkzis.pomona.R
import com.carkzis.pomona.data.Repository
import com.carkzis.pomona.launchFragmentInHiltContainer
import com.carkzis.pomona.ui.detail.FruitDetailFragment
import com.schibsted.spain.barista.rule.cleardata.ClearDatabaseRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class FruitDetailFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    /*
    This will clear the database before each test to ensure no fruit are
    already held in the database.
     */
    @get:Rule
    var clearDatabaseRule = ClearDatabaseRule()

    @Inject
    lateinit var repository: Repository

    @Before
    fun init() {
        hiltRule.inject()
    }

    /*
    Note: we can provide our own arguments, so no internet connection or data held
    in the database is required.
     */

    @Test
    fun fruitDetailFragment_whenNavigatedTo_displayFruitDetailsOnUI() {
        // Given navArgs for a fruit to be displayed.
        val args = arrayOf<String>("Ugly", "£50.00", "80.0kg")
        // Note: ugly fruit are like grapefruit, and (subjectively) taste just as bad.

        // On the FruitListFragment screen, using the provided fruitDetail args.
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<FruitDetailFragment>(bundleOf("fruitDetail" to args)) {
            navController.setGraph(R.navigation.navigation)
            Navigation.setViewNavController(requireView(), navController)
        }

        // Assert that the ugly fruit details are displayed.
        Espresso.onView(ViewMatchers.withText("Ugly"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("£50.00"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}
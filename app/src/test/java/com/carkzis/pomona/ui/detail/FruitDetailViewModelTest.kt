package com.carkzis.pomona.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.carkzis.pomona.getOrAwaitValue
import com.carkzis.pomona.ui.DomainFruit
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FruitDetailViewModelTest {

    private lateinit var fruitDetailViewModel: FruitDetailViewModel

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        fruitDetailViewModel = FruitDetailViewModel()
    }

    @Test
    fun setUpFruitDetail_domainFruitUsedAsArgument_domainFruitPostedToLiveData() {
        // Given a DomainFruit object.
        val fruitType = "Durian"
        val fruitPrice = "£60.42"
        val fruitWeight = "100kg"
        val fruit = DomainFruit(fruitType, fruitPrice, fruitWeight) // It's pretty heavy.

        // When the method setUpFruitDetail is called.
        fruitDetailViewModel.setUpFruitDetail(fruit)

        // Assert that the same fruit is posted to the LiveData.
        assertThat(fruitDetailViewModel.fruit.getOrAwaitValue().type, `is`(fruitType))
        assertThat(fruitDetailViewModel.fruit.getOrAwaitValue().price, `is`(fruitPrice))
        assertThat(fruitDetailViewModel.fruit.getOrAwaitValue().weight, `is`(fruitWeight))
    }
}
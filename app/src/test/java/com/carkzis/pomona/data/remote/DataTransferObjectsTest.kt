package com.carkzis.pomona.data.remote

import com.carkzis.pomona.data.local.DatabaseFruit
import com.carkzis.pomona.data.local.asDomainModel
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test

class DataTransferObjectsTest {

    @Test
    fun fruitContainerAsDatabaseModel_correctFormat_convertNetworkFruitToDatabaseFruit() {
        // Given a FruitContainer with a list of NetworkFruit.
        val fruitContainer = FruitContainer(mutableListOf(NetworkFruit("orange", "1000", "1233")))

        // Call the extension function to return a list of one DatabaseFruit object.
        val databaseFruit = fruitContainer.asDatabaseModel()

        // Assert that the DatabaseFruit holds the correct information converted from databaseFruit.
        MatcherAssert.assertThat(databaseFruit[0].type, CoreMatchers.`is`("orange"))
        MatcherAssert.assertThat(databaseFruit[0].price, CoreMatchers.`is`(1000))
        MatcherAssert.assertThat(databaseFruit[0].weight, CoreMatchers.`is`(1233))
    }

    @Test
    fun fruitContainerAsDatabaseModel_priceFormatIncorrect_convertButWithZeroForPrice() {
        // Given a FruitContainer with a list of NetworkFruit, with erroneous "Z" added to price.
        val fruitContainer = FruitContainer(mutableListOf(NetworkFruit("orange", "1000Z", "1233")))

        // Call the extension function to return a list of one DatabaseFruit object.
        val databaseFruit = fruitContainer.asDatabaseModel()

        /*
        Assert that the DatabaseFruit holds the same information for type and weight,
        but -1 for price assuming the NumberFormatException was caught.
         */
        MatcherAssert.assertThat(databaseFruit[0].type, CoreMatchers.`is`("orange"))
        MatcherAssert.assertThat(databaseFruit[0].price, CoreMatchers.`is`(-1))
        MatcherAssert.assertThat(databaseFruit[0].weight, CoreMatchers.`is`(1233))
    }

    @Test
    fun fruitContainerAsDatabaseModel_weightFormatIncorrect_convertButWithZeroForWEight() {
        // Given a FruitContainer with a list of NetworkFruit, with erroneous "Z" added to weight.
        val fruitContainer = FruitContainer(mutableListOf(NetworkFruit("orange", "1000", "1233Z")))

        // Call the extension function to return a list of one DatabaseFruit object.
        val databaseFruit = fruitContainer.asDatabaseModel()

        /*
        Assert that the DatabaseFruit holds the same information for type and price,
        but -1 for price assuming the NumberFormatException was caught.
         */
        MatcherAssert.assertThat(databaseFruit[0].type, CoreMatchers.`is`("orange"))
        MatcherAssert.assertThat(databaseFruit[0].price, CoreMatchers.`is`(1000))
        MatcherAssert.assertThat(databaseFruit[0].weight, CoreMatchers.`is`(-1))
    }

}
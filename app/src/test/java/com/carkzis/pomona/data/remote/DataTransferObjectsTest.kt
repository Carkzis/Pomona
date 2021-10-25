package com.carkzis.pomona.data.remote

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class DataTransferObjectsTest {

    @Test
    fun fruitContainerAsDatabaseModel_correctFormat_convertNetworkFruitToDatabaseFruit() {
        // Given a FruitContainer with a list of NetworkFruit.
        val fruitContainer = FruitContainer(mutableListOf(NetworkFruit("orange", "1000", "1233")))

        // Call the extension function to return a list of one DatabaseFruit object.
        val databaseFruit = fruitContainer.asDatabaseModel()

        // Assert that the DatabaseFruit holds the correct information converted from databaseFruit.
        assertThat(databaseFruit[0].type, `is`("orange"))
        assertThat(databaseFruit[0].price, `is`(1000))
        assertThat(databaseFruit[0].weight, `is`(1233))
    }

    @Test
    fun fruitContainerAsDatabaseModel_priceFormatIncorrect_convertButWithMinusOneForPrice() {
        // Given a FruitContainer with a list of NetworkFruit, with erroneous "Z" added to price.
        val fruitContainer = FruitContainer(mutableListOf(NetworkFruit("orange", "1000Z", "1233")))

        // Call the extension function to return a list of one DatabaseFruit object.
        val databaseFruit = fruitContainer.asDatabaseModel()

        /*
        Assert that the DatabaseFruit holds the same information for type and weight,
        but -1 for price assuming the NumberFormatException was caught.
         */
        assertThat(databaseFruit[0].type, `is`("orange"))
        assertThat(databaseFruit[0].price, `is`(-1))
        assertThat(databaseFruit[0].weight, `is`(1233))
    }

    @Test
    fun fruitContainerAsDatabaseModel_weightFormatIncorrect_convertButWithMinusOneForWeight() {
        // Given a FruitContainer with a list of NetworkFruit, with erroneous "Z" added to weight.
        val fruitContainer = FruitContainer(mutableListOf(NetworkFruit("orange", "1000", "1233Z")))

        // Call the extension function to return a list of one DatabaseFruit object.
        val databaseFruit = fruitContainer.asDatabaseModel()

        /*
        Assert that the DatabaseFruit holds the same information for type and price,
        but -1 for price assuming the NumberFormatException was caught.
         */
        assertThat(databaseFruit[0].type, `is`("orange"))
        assertThat(databaseFruit[0].price, `is`(1000))
        assertThat(databaseFruit[0].weight, `is`(-1))
    }

    @Test
    fun fruitContainerAsDatabaseModel_priceMissing_convertButWithMinusOneForPrice() {
        // Given a FruitContainer with a list of NetworkFruit, with a missing price.
        val fruitContainer = FruitContainer(mutableListOf(NetworkFruit("orange", weight = "1233")))

        // Call the extension function to return a list of one DatabaseFruit object.
        val databaseFruit = fruitContainer.asDatabaseModel()

        /*
        Assert that the DatabaseFruit holds the same information for type and weight,
        but -1 for price assuming the NumberFormatException was caught.
         */
        assertThat(databaseFruit[0].type, `is`("orange"))
        assertThat(databaseFruit[0].price, `is`(-1))
        assertThat(databaseFruit[0].weight, `is`(1233))
    }

    @Test
    fun fruitContainerAsDatabaseModel_weightMissing_convertButWithMinusOneForWeight() {
        // Given a FruitContainer with a list of NetworkFruit, with a missing weight.
        val fruitContainer = FruitContainer(mutableListOf(NetworkFruit("orange", "1000")))

        // Call the extension function to return a list of one DatabaseFruit object.
        val databaseFruit = fruitContainer.asDatabaseModel()

        /*
        Assert that the DatabaseFruit holds the same information for type and price,
        but -1 for price assuming the NumberFormatException was caught.
         */
        assertThat(databaseFruit[0].type, `is`("orange"))
        assertThat(databaseFruit[0].price, `is`(1000))
        assertThat(databaseFruit[0].weight, `is`(-1))
    }

    @Test
    fun fruitContainerAsDatabaseModel_typeMissing_excludeFromResults() {
        /*
        Given a FruitContainer with a list of two NetworkFruit objects, with one item
        missing a value type.
         */
        val fruitContainer = FruitContainer(
            mutableListOf(
                NetworkFruit(
                    "orange", "1000", "1233"
                ),
                NetworkFruit(price = "40", weight = "50")
            )
        )

        // Call the extension function to return a list of one DatabaseFruit object.
        val databaseFruit = fruitContainer.asDatabaseModel()

        /*
        Assert that the DatabaseFruit list only holds one item as the latter item was excluding
        due to holding no type. The first item should not be excluded, and should have the correct
        details.
         */
        assertThat(databaseFruit[0].type, `is`("orange"))
        assertThat(databaseFruit[0].price, `is`(1000))
        assertThat(databaseFruit[0].weight, `is`(1233))
        assertThat(databaseFruit.size, `is`(1))
    }

}
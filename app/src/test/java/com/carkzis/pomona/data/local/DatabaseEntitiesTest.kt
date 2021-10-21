package com.carkzis.pomona.data.local

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class DatabaseEntitiesTest {

    @Test
    fun listDatabaseFruitAsDomainModel_convertDatabaseFruitToDomainFruit() {
        // Given a DatabaseFruit.
        val databaseFruit = mutableListOf(DatabaseFruit("orange", 1000, 1233))

        // Call the extension function to return a list of one DomainFruit object.
        val domainFruit = databaseFruit.asDomainModel()

        // Assert that the DomainFruit holds the correct information converted from databaseFruit.
        assertThat(domainFruit[0].type, `is`("Orange"))
        assertThat(domainFruit[0].price, `is`("£10.00"))
        assertThat(domainFruit[0].weight, `is`("1.233kg"))
    }

    @Test
    fun capitalise_oneLetter_letterCapitalisedWithoutError() {
        // Given a single letter String in lowercase.
        val name = "m"

        // Call the method to capitalise it.
        val capitalisedName = capitalise(name)

        // Assert that the name is now capitalised.
        assertThat(capitalisedName, `is`("M"))
    }

    @Test
    fun capitalise_fullName_firstLetterCapitalised() {
        // Given a single letter String in lowercase.
        val name = "Marc"

        // Call the method to capitalise it.
        val capitalisedName = capitalise(name)

        // Assert that the name is now capitalised.
        assertThat(capitalisedName, `is`("Marc"))
    }

    @Test
    fun convertToPoundsAndPennies_zeroPennies_returnZeroInCurrencyFormat() {
        // Given zero pennies.
        val pennies = 0

        // Call the method to convert and format to pounds and pennies.
        val poundsAndPennies = convertToPoundsAndPennies(pennies)

        // Assert that we get the correct formatting of pounds and pennies.
        assertThat(poundsAndPennies, `is`("£0.00"))
    }

    @Test
    fun convertToPoundsAndPennies_lessThan100Pennies_returnZeroPoundsSomePennies() {
        // Given less than 100 pennies but more than zero.
        val pennies = 99

        // Call the method to convert and format to pounds and pennies.
        val poundsAndPennies = convertToPoundsAndPennies(pennies)

        // Assert that we get the correct formatting of pounds and pennies.
        assertThat(poundsAndPennies, `is`("£0.99"))
    }

    @Test
    fun convertToPoundsAndPennies_over100Pennies_returnPoundsAndPennies() {
        // Given many pennies.
        val pennies = 5555

        // Call the method to convert and format to pounds and pennies.
        val poundsAndPennies = convertToPoundsAndPennies(pennies)

        // Assert that we get the correct formatting of pounds and pennies.
        assertThat(poundsAndPennies, `is`("£55.55"))
    }

    @Test
    fun convertToPoundsAndPennies_negativePennies_returnErrorMessage() {
        // Given many pennies.
        val pennies = -1

        // Call the method to convert and format to pounds and pennies.
        val poundsAndPennies = convertToPoundsAndPennies(pennies)

        // Assert that we get the error message.
        assertThat(poundsAndPennies, `is`("???"))
    }

    @Test
    fun convertToKilograms_zeroGrams_returnZeroInGramsFormat() {
        // Given zero grams.
        val grams = 0

        // Call the method to convert and format to kilograms.
        val kilograms = convertToKilograms(grams)

        // Assert that we get the correct amount of kilograms in the correct format.
        assertThat(kilograms, `is`("0.0kg"))
    }

    @Test
    fun convertToKilograms_oneGram_returnOneGramInKilogramsFormat() {
        // Given one grams.
        val grams = 1

        // Call the method to convert and format to kilograms.
        val kilograms = convertToKilograms(grams)

        // Assert that we get the correct amount of kilograms in the correct format.
        assertThat(kilograms, `is`("0.001kg"))
    }

    @Test
    fun convertToKilograms_thousandGrams_returnOneKilogramInCorrectFormat() {
        // Given one grams.
        val grams = 1000

        // Call the method to convert and format to kilograms.
        val kilograms = convertToKilograms(grams)

        // Assert that we get the correct amount of kilograms in the correct format.
        assertThat(kilograms, `is`("1.0kg"))
    }

    @Test
    fun convertToKilograms_negativeGrams_returnErrorMessage() {
        // Given one grams.
        val grams = -1

        // Call the method to convert and format to kilograms.
        val kilograms = convertToKilograms(grams)

        // Assert that we get the error message.
        assertThat(kilograms, `is`("???"))
    }
}
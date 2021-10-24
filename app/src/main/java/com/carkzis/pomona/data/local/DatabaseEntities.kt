package com.carkzis.pomona.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.carkzis.pomona.ui.DomainFruit
import java.text.NumberFormat
import java.util.*

/**
 * This represents a fruit entity in the database.
 */
@Entity
data class DatabaseFruit(
    @PrimaryKey
    val type: String,
    val price: Int,
    val weight: Int
)

/**
 * This maps the [DatabaseFruit] to the fruit entities used in displaying the data to the UI,
 * formatted appropriately.
 */
fun List<DatabaseFruit>.asDomainModel(): List<DomainFruit> {
    return map {
        DomainFruit(
            type = capitalise(it.type),
            price = convertToPoundsAndPennies(it.price),
            weight = convertToKilograms(it.weight)
        )
    }
}

/**
 * Capitalises the first letter in a String, used here with respect to the [type] of fruit.
 */
fun capitalise(type: String) : String {
    // I have assumed that fruit with spaces in their names only have first word capitilised.
    val firstLetter = type.substring(0, 1).uppercase()
    return if (type.length == 1) firstLetter else firstLetter + type.substring(1)
}

/**
 * Converts an amount of [pennies] into pounds and pence, in the format of £xx.xx, and
 * returns a String.
 * Returns "???" if there is an error, where the argument amount will be -1.
 */
fun convertToPoundsAndPennies(pennies: Int) : String {
    if (pennies < 0) return "???"
    val poundsAndPennies = (pennies.toDouble() / 100.0)
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.UK)
    return currencyFormat.format(poundsAndPennies)
}

/**
 * Converts an amount of [grams] into kilograms, in the format of x.xkg., and returns a String.
 * Returns "???" if there is an error, where the argument amount will be -1.
 */
fun convertToKilograms(grams: Int) : String {
    return if (grams >= 0) "${grams.toDouble() / 1000.0}kg" else "???"
}
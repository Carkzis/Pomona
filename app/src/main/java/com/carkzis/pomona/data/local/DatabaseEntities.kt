package com.carkzis.pomona.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.carkzis.pomona.ui.DomainFruit
import java.text.NumberFormat
import java.util.*

@Entity
data class DatabaseFruit(
    @PrimaryKey
    val type: String,
    val price: Int,
    val weight: Int
)

fun List<DatabaseFruit>.asDomainModel(): List<DomainFruit> {
    return map {
        DomainFruit(
            type = capitalise(it.type),
            price = convertToPoundsAndPennies(it.price),
            weight = convertToKilograms(it.weight)
        )
    }
}

fun capitalise(name: String) : String {
    // I have assumed that fruits with spaces in their names only have first segment capitilised.
    val firstLetter = name.substring(0, 1).uppercase()
    return if (name.length == 1) firstLetter else firstLetter + name.substring(1)
}

fun convertToPoundsAndPennies(pennies: Int) : String {
    if (pennies < 0) return "???"
    val poundsAndPennies = (pennies.toDouble() / 100.0)
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.UK)
    return currencyFormat.format(poundsAndPennies)
}

fun convertToKilograms(grams: Int) : String {
    // Return "???" if we get a negative weight.
    return if (grams >= 0) "${grams.toDouble() / 1000.0}kg" else "???"
}
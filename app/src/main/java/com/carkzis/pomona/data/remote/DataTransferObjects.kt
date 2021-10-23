package com.carkzis.pomona.data.remote

import android.provider.ContactsContract
import com.carkzis.pomona.data.local.DatabaseFruit
import com.carkzis.pomona.stats.UsageStatsManager
import com.squareup.moshi.JsonClass
import java.io.IOException
import java.lang.NumberFormatException

@JsonClass(generateAdapter = true)
data class NetworkFruit(
    val type: String,
    val price: String,
    val weight: String
)

@JsonClass(generateAdapter = true)
data class FruitContainer(
    val fruit: List<NetworkFruit>
)

fun FruitContainer.asDatabaseModel(): List<DatabaseFruit> {
    return fruit.map {
        DatabaseFruit(
            type = it.type,
            price = try {
                it.price.toInt()
            } catch (e: NumberFormatException) {
                // Generate error stats.
                UsageStatsManager.generateErrorEventStats(e)
                -1
            },
            weight = try {
                it.weight.toInt()
            } catch (e: NumberFormatException) {
                // Generate error stats.
                UsageStatsManager.generateErrorEventStats(e)
                -1
            },
        )
    }
}
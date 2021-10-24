package com.carkzis.pomona.data.remote

import com.carkzis.pomona.data.local.DatabaseFruit
import com.carkzis.pomona.stats.UsageStatsManager
import com.squareup.moshi.JsonClass

/**
 * This parses the first level of the network response from the server.
 */
@JsonClass(generateAdapter = true)
data class FruitContainer(
    val fruit: List<NetworkFruit>
)

/**
 * This represents the data transfer objects obtained from the network.
 */
@JsonClass(generateAdapter = true)
data class NetworkFruit(
    val type: String = "",
    val price: String = "",
    val weight: String = ""
)

/**
 * This maps the [NetworkFruit] to the database entities.
 * Any fruit without a type will have a default type value of "", and these will be filtered out
 * as it is a mandatory field.
 * Assigns -1 if the price or weight cannot be converted into integers; these are not
 * mandatory fields.
 */
fun FruitContainer.asDatabaseModel(): List<DatabaseFruit> {
    return fruit.filter {
        it.type != ""
    }.map {
            DatabaseFruit(
                type = it.type,
                // Try converting the price String into an Int, return -1 if it fails.
                price = try {
                    it.price.toInt()
                } catch (e: NumberFormatException) {
                    // Generate error stats.
                    UsageStatsManager.generateErrorEventStats(e)
                    -1
                },
                // Try converting the price String into an Int, return -1 if it fails.
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

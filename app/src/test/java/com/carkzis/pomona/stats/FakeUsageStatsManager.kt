package com.carkzis.pomona.stats

import com.carkzis.pomona.data.remote.StatsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class FakeUsageStatsManager : StatsManager() {

    var queryPair = Pair<String?, String?>(null, null)
    var timeToCalculate = false
    var initialTime = 0L

    override fun generateEventStats(event: String, data: String) {
        queryPair = Pair(event, data)
    }

    override fun generateLoadEventStats(timeMSec: Int) {
        if (timeMSec < 0 ) return // There is an error with the time elapsed.
        generateEventStats("load", timeMSec.toString())
    }

    override fun generateDisplayEventStats() {
        // TODO: Consider if the user exits in between the start and stop.
        if (!timeToCalculate) {
            initialTime = System.currentTimeMillis()
            timeToCalculate = true
        } else {
            val difference = (System.currentTimeMillis() - initialTime).toString()
            generateEventStats("display", difference)
            timeToCalculate = false
            initialTime = 0L
        }
    }

    override fun generateErrorEventStats(exception: Exception) {
        TODO("Not yet implemented")
    }


}
package com.carkzis.pomona.stats

import com.carkzis.pomona.data.remote.StatsApi
import kotlinx.coroutines.*

object UsageStatsManager : StatsManager() {

    override fun generateEventStats(event: String, data: String) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            StatsApi.retroService.generateUsageStats(event, data)
        }
    }

    override fun generateLoadEventStats(timeMSec: Int) {
        TODO("Not yet implemented")
    }

    override fun generateDisplayEventStats(timeMSec: Int) {
        TODO("Not yet implemented")
    }

    override fun generateErrorEventStats(exception: Exception) {
        TODO("Not yet implemented")
    }

}
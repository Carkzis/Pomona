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

}
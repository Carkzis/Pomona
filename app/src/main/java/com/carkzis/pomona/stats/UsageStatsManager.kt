package com.carkzis.pomona.stats

import com.carkzis.pomona.data.remote.FruitApi
import com.carkzis.pomona.data.remote.StatsApi
import kotlinx.coroutines.*
import timber.log.Timber

object UsageStatsManager : StatsManager() {

    override fun generateEventStats(event: String, data: String) {
        Timber.e("Event: $event, Data: $data")
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            try {
                StatsApi.retroService.generateUsageStats(event, data)

            } catch (e: Exception) {
                Timber.e(e.toString())
            }

        }
    }

}
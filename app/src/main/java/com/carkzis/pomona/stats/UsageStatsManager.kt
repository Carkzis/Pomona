package com.carkzis.pomona.stats

import com.carkzis.pomona.data.remote.StatsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Singleton to generate usage stats via a network call using retrofit.
 */
object UsageStatsManager : StatsManager() {

    /**
     * Generates usage stats given an [event] and [data] via a network call.
     */
    override fun generateEventStats(event: String, data: String) {
        Timber.e("Event: $event, Data: $data")
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            try {
                StatsApi.retroService.generateUsageStats(event, data)
            } catch (e: Exception) {
                Timber.e(e.toString()) // Show any exception in Logcat.
            }
        }
    }

}
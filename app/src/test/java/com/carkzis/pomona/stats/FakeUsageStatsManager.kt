package com.carkzis.pomona.stats

class FakeUsageStatsManager : StatsManager() {

    var queryPair = Pair<String?, String?>(null, null)

    /**
     * This takes in an [event] and [data] and adds it to the [queryPair].
     */
    override fun generateEventStats(event: String, data: String) {
        queryPair = Pair(event, data)
    }

}
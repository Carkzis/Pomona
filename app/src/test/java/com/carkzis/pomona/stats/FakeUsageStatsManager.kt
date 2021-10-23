package com.carkzis.pomona.stats

class FakeUsageStatsManager : StatsManager() {

    var queryPair = Pair<String?, String?>(null, null)

    override fun generateEventStats(event: String, data: String) {
        queryPair = Pair(event, data)
    }

}
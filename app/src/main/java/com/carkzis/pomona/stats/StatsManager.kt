package com.carkzis.pomona.stats

abstract class StatsManager {

    abstract fun generateEventStats(event: String, data: String)
    abstract fun generateLoadEventStats(timeMSec: Int)
    abstract fun generateDisplayEventStats(timeMSec: Int)
    abstract fun generateErrorEventStats(exception: Exception)

}
package com.carkzis.pomona.stats

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
        // Get the formatted exception name.
        val exceptionName = formatExceptionName(exception)
        // Get the full class name.
        val fullClassName = exception.stackTrace[0].className
        // Get the simple class name in lowercase.
        val simpleClassName = fullClassName
            .substring(fullClassName.lastIndexOf('.') + 1).lowercase()
        // Get the line number.
        val lineNumber = exception.stackTrace[0].lineNumber

        // Form an event message.
        val eventMessage = "$exceptionName on line $lineNumber in $simpleClassName"

        generateEventStats("error", eventMessage)
    }

}
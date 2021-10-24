package com.carkzis.pomona.stats

/**
 * Abstract class containing methods for generating event statistics.
 * Inherited by UsageStatsManager and FakeUsageStatsManager.
 */
abstract class StatsManager {

    var initialTime = 0L // Stores the current time in milliseconds.

    /**
     * This we generate a network call in UsageStatsManager, or add event and data
     * details to a Pair in FakeUsageStatsManager.
     */
    protected abstract fun generateEventStats(event: String, data: String)

    /**
     * Generates the "load" event stats using the amount of milliseconds [timeMSec] it
     * took for an event to complete.
     */
    fun generateLoadEventStats(timeMSec: Long) {
        if (timeMSec < 0) return // There is an error with the time elapsed.
        generateEventStats("load", timeMSec.toString())
    }

    /**
     * Starts the timer for a display event from when the user initiated the request to display
     * a screen. It will overwrite a previous call even if the corresponding
     * [stopDisplayEventStats] method has not been called.
     */
    fun startDisplayEventStats() {
        initialTime = System.currentTimeMillis()
    }

    /**
     * Stops the timer for a display event, and posts the details to [generateEventStats].
     */
    fun stopDisplayEventStats() {
        if (initialTime <= 0) return // We haven't called startDisplayEventStats().
        val difference = (System.currentTimeMillis() - initialTime).toString()
        generateEventStats("display", difference)
        initialTime = 0L
    }

    /**
     * Generates error event stats using an [exception] as an argument. Posts the formatted
     * details to [generateEventStats] to be used in the network call.
     */
    fun generateErrorEventStats(exception: Exception) {
        // Get the formatted exception name.
        val exceptionName = formatExceptionName(exception)
        // Get the full class name.
        val className = formatClassName(exception.stackTrace[0].className)
        // Get the line number.
        val lineNumber = exception.stackTrace[0].lineNumber

        // Form an event message.
        val eventMessage = "$exceptionName on line $lineNumber in $className"

        // Forwards the formatted details to be used in the network call.
        generateEventStats("error", eventMessage)
    }

    /**
     * This ensures that the exception name is in the appropriate format for the network call.
     */
    fun formatExceptionName(exception: Exception): String {
        // Get the simple name of the exception.
        val exceptionName = exception::class.java.simpleName
        // Split the name up by uppercase letters.
        val splitNameList = exceptionName.split(Regex("(?=\\p{Upper})"))
        // Removes the first, empty item.
        val splitNameSubList = splitNameList.subList(1, splitNameList.size)
        // Return a string split by spaces in lowercase. Spaces will be encoded by retrofit.
        return splitNameSubList.joinToString(" ").lowercase()
    }

    /**
     * This ensures that the class name is in the appropriate format for the network call.
     */
    fun formatClassName(fullClassName: String): String {
        if (fullClassName.isEmpty()) return "unknown"
        // Get the simple class name in lowercase.
        val simpleClassName = fullClassName
            .substring(fullClassName.lastIndexOf('.') + 1).lowercase()
        // Return the simple class name, with method removed (follows a $) if applicable.
        return if (simpleClassName.contains("$")) {
            simpleClassName.substring(0, simpleClassName.indexOf("$"))
        } else {
            simpleClassName
        }
    }

}
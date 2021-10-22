package com.carkzis.pomona.stats

abstract class StatsManager {

    abstract fun generateEventStats(event: String, data: String)
    abstract fun generateLoadEventStats(timeMSec: Int)
    abstract fun generateDisplayEventStats()
    abstract fun generateErrorEventStats(exception: Exception)

    fun formatExceptionName(exception: Exception) : String {
        var exceptionName = exception::class.java.simpleName // Get the simple name of the exception.
        // Split the name up by uppercase letters.
        val splitNameList = exceptionName.split(Regex("(?=\\p{Upper})"))
        // Removes the first, empty item.
        val splitNameSubList = splitNameList.subList(1, splitNameList.size)
        // Return a string split by spaces in lowercase.
        return splitNameSubList.joinToString(" ").lowercase()
    }

}
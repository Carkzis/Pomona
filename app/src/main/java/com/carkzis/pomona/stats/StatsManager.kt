package com.carkzis.pomona.stats

abstract class StatsManager {

    var timeToCalculate = false
    var initialTime = 0L
    protected abstract fun generateEventStats(event: String, data: String)

    fun generateLoadEventStats(timeMSec: Long) {
        if (timeMSec < 0) return // There is an error with the time elapsed.
        generateEventStats("load", timeMSec.toString())
    }

    fun generateDisplayEventStats() {
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

    fun generateErrorEventStats(exception: Exception) {
        // Get the formatted exception name.
        val exceptionName = formatExceptionName(exception)
        // Get the full class name.
        val className = formatClassName(exception.stackTrace[0].className)
        // Get the line number.
        val lineNumber = exception.stackTrace[0].lineNumber

        // Form an event message.
        val eventMessage = "$exceptionName on line $lineNumber in $className"

        generateEventStats("error", eventMessage)
    }

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
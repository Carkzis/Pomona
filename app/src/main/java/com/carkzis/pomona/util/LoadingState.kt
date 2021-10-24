package com.carkzis.pomona.util

import java.lang.Exception

/**
 * This class holds the loading state of network calls to the FruitAPI.
 */
sealed class LoadingState {
    data class Loading(val message: Int) : LoadingState()
    data class Success(val message: Int, val dataSize: Int) : LoadingState()
    data class Error(val message: Int, val exception: Exception) : LoadingState()
}
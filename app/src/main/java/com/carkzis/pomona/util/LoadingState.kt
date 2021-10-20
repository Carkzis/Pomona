package com.carkzis.pomona.util

import java.lang.Exception

sealed class LoadingState<out T> {
    data class Loading<T>(val message: Int) : LoadingState<T>()
    data class Success<T>(val message: Int) : LoadingState<T>()
    data class Error<T>(val message: Int, val exception: Exception) : LoadingState<T>()
}
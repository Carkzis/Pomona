package com.carkzis.pomona.util

import android.content.Context
import android.widget.Toast

/**
 * Extension function that generates a Toast given a [message].
 */
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).run {
        show()
    }
}
package com.carkzis.pomona.util

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.core.content.ContextCompat
import com.carkzis.pomona.R

/**
 * Returns a filter for the specific fruit type. Takes in the [type] and [context].
 * Note: This list is exhaustive, if there were to be additional fruits they would use the
 * default colour.  Assuming we had access to the Fruit API, an additional field could be added
 * that could decide the colour for each fruit.
 */
internal fun getFruitColourFilter(type: String, context: Context) = when (type) {
    "Apple" -> PorterDuffColorFilter(ContextCompat.getColor(context, R.color.apple), PorterDuff.Mode.SRC_IN)
    "Banana" -> PorterDuffColorFilter(ContextCompat.getColor(context, R.color.banana), PorterDuff.Mode.SRC_IN)
    "Blueberry" -> PorterDuffColorFilter(ContextCompat.getColor(context, R.color.blueberry), PorterDuff.Mode.SRC_IN)
    "Orange" -> PorterDuffColorFilter(ContextCompat.getColor(context, R.color.orange), PorterDuff.Mode.SRC_IN)
    "Pear" -> PorterDuffColorFilter(ContextCompat.getColor(context, R.color.pear), PorterDuff.Mode.SRC_IN)
    "Strawberry" -> PorterDuffColorFilter(ContextCompat.getColor(context, R.color.strawberry), PorterDuff.Mode.SRC_IN)
    "Kumquat" -> PorterDuffColorFilter(ContextCompat.getColor(context, R.color.kumquat), PorterDuff.Mode.SRC_IN)
    "Pitaya" -> PorterDuffColorFilter(ContextCompat.getColor(context, R.color.pitaya), PorterDuff.Mode.SRC_IN)
    "Kiwi" -> PorterDuffColorFilter(ContextCompat.getColor(context, R.color.kiwi), PorterDuff.Mode.SRC_IN)
    else -> PorterDuffColorFilter(ContextCompat.getColor(context, R.color.primaryColor), PorterDuff.Mode.SRC_IN)
}
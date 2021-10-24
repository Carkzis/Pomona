package com.carkzis.pomona.di

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * This is a test activity which allows the use of Hilt in our instrumented tests.
 */
@AndroidEntryPoint
class HiltTestActivity : AppCompatActivity()
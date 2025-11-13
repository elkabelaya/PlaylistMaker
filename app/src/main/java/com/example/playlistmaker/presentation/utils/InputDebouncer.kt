package com.example.playlistmaker.presentation.utils

import android.os.Handler
import android.os.Looper

class InputDebouncer {
    private val handler = Handler(Looper.getMainLooper())
    private var inputRunnable: Runnable? = null

    fun debounce(callBack:() -> Unit) {
        inputRunnable?.let {
            handler.removeCallbacks(it)
        }
        inputRunnable = Runnable{ callBack() }
        inputRunnable?.let {
            handler.postDelayed(it, INPUT_DEBOUNCE_DELAY)
        }
    }

    companion object {
        private const val INPUT_DEBOUNCE_DELAY = 2000L
    }
}
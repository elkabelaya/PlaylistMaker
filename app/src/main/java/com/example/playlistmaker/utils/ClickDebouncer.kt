package com.example.playlistmaker.utils

import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

interface ClickDebouncerInterface {
    fun canClickDebounced() : Boolean
}

open class ClickDebouncer(): ClickDebouncerInterface {
    private var isClickAllowed: Boolean = true
    private val handler = Handler(Looper.getMainLooper())
    override fun canClickDebounced() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 500L
    }
}
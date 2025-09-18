package com.example.playlistmaker

import android.content.SharedPreferences
import com.example.Preferences

class SearchHistoryManager(val prefs: Preferences, val maxCount: Int) {
    var elements = mutableListOf<Track>()
        private set

    init {
        elements = prefs.searchHistory.toMutableList()
    }

    fun add(element: Track) {
        val elementIndex = elements.indexOf(element)
        if (elementIndex > -1) {
            elements.removeAt(elementIndex)
        }

        elements.add(0, element)

        if (elements.size > maxCount) {
            elements.removeAt(elements.lastIndex)
        }

        save()
    }

    fun clear() {
        elements.clear()
        save()
    }

    private fun save() {
        prefs.searchHistory = elements
    }
}
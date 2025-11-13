package com.example.playlistmaker.domain.impl

import com.example.playlistmaker.domain.api.HistoryInteractor
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.domain.repository.PreferencesRepository

class HistoryInteractorImpl(val repository: PreferencesRepository, val maxCount: Int):
    HistoryInteractor {
    override var elements = mutableListOf<Track>()
        private set

    init {
        elements = repository.searchHistory.toMutableList()
    }

    override fun add(element: Track) {
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

    override fun clear() {
        elements.clear()
        save()
    }

    private fun save() {
        repository.searchHistory = elements
    }
}
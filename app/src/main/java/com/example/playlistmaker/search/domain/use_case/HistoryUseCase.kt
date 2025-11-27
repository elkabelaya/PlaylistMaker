package com.example.playlistmaker.search.domain.use_case

import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.model.Tracks

interface HistoryUseCase {
    val elements: Tracks
    fun add(element: Track)
    fun clear()
}
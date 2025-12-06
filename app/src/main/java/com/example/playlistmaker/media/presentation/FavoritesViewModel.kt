package com.example.playlistmaker.media.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.model.Tracks

abstract class FavoritesViewModel: ViewModel() {
    abstract fun observeUrl(): LiveData<Tracks>
    abstract fun select(track: Track)
}
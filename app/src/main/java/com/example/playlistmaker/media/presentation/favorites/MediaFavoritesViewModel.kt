package com.example.playlistmaker.media.presentation.favorites

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.presentation.StateFullViewModel
import com.example.playlistmaker.media.domain.model.MediaFavoritesState

abstract class MediaFavoritesViewModel: StateFullViewModel<MediaFavoritesState>, ViewModel() {
    abstract fun select(track: Track)
}
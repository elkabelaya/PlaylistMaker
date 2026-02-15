package com.example.playlistmaker.newplaylist.presentation

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.presentation.StateFullViewModel
import com.example.playlistmaker.media.domain.model.MediaFavoritesState

abstract class NewPlaylistViewModel: StateFullViewModel<MediaFavoritesState>, ViewModel() {
    //abstract fun select(track: Track)
}
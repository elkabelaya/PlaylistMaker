package com.example.playlistmaker.media.presentation.preview

import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.media.domain.model.MediaFavoritesState
import com.example.playlistmaker.media.presentation.favorites.MediaFavoritesViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MediaFavoritesViewModelMock(state: MediaFavoritesState): MediaFavoritesViewModel() {
    private val _state = MutableStateFlow<MediaFavoritesState>(MediaFavoritesState.Loading)
    override var state: StateFlow<MediaFavoritesState> = _state.asStateFlow()
    init {
        _state.value = state
    }

    override fun select(track: Track) {}
}
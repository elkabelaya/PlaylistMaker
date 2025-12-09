package com.example.playlistmaker.media.domain.model

import com.example.playlistmaker.common.domain.model.ErrorState

sealed class  MediaFavoritesState {
    data object Loading: MediaFavoritesState()
    data class Error(val errorState: ErrorState): MediaFavoritesState()
    data class Tracks(val tracks: Tracks): MediaFavoritesState()
}
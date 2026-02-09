package com.example.playlistmaker.media.domain.model

import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.common.domain.model.Tracks

sealed class  MediaFavoritesState {
    data object Loading: MediaFavoritesState()
    data class Error(val errorState: ErrorState): MediaFavoritesState()
    data class Data(val tracks: Tracks): MediaFavoritesState()
}
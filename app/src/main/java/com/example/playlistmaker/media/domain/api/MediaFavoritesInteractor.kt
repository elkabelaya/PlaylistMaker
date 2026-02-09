package com.example.playlistmaker.media.domain.api

import com.example.playlistmaker.common.domain.api.StateFullInteractor
import com.example.playlistmaker.media.domain.model.MediaFavoritesState
import kotlinx.coroutines.flow.Flow

interface MediaFavoritesInteractor: StateFullInteractor<MediaFavoritesState> {
    suspend fun getFracks()
}
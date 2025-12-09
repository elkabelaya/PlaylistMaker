package com.example.playlistmaker.media.domain.api

import com.example.playlistmaker.common.domain.api.StateFullInteractor
import com.example.playlistmaker.media.domain.model.MediaFavoritesState

interface MediaFavoritesInteractor: StateFullInteractor<MediaFavoritesState> {
    fun getFracks()
}
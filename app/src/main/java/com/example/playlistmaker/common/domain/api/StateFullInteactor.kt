package com.example.playlistmaker.common.domain.api

import com.example.playlistmaker.media.domain.model.MediaFavoritesState

interface StateFullInteractor<T> {
    fun onState(state: (T) -> Unit)
}
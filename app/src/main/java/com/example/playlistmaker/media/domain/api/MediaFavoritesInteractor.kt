package com.example.playlistmaker.media.domain.api
import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.common.domain.model.Tracks
import kotlinx.coroutines.flow.Flow

interface MediaFavoritesInteractor {
    suspend fun getFracks(): Flow<Pair<Tracks?, ErrorState?>>
}
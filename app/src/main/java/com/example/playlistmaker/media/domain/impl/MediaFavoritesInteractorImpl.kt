package com.example.playlistmaker.media.domain.impl

import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.common.domain.model.Tracks
import com.example.playlistmaker.common.domain.repository.FavoriteTracksRepository
import com.example.playlistmaker.media.domain.api.MediaFavoritesInteractor
import com.example.playlistmaker.media.domain.model.MediaFavoritesState
import com.example.playlistmaker.media.domain.repository.MediaFavoritesErrorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MediaFavoritesInteractorImpl(
    val repository: FavoriteTracksRepository,
    val errorRepository: MediaFavoritesErrorRepository
): MediaFavoritesInteractor {

    override suspend fun getFracks(): Flow<Pair<Tracks?, ErrorState?>> = flow {
        repository.get()
            .collect { tracks ->
                if (tracks.isEmpty()) {
                    emit(Pair(null, ErrorState.Empty(errorRepository.getErrorText())))
                } else {
                    emit(Pair(tracks, null))
                }
            }
    }
}
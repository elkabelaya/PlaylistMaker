package com.example.playlistmaker.media.domain.impl

import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Playlists
import com.example.playlistmaker.media.domain.api.MediaPlaylistsInteractor
import com.example.playlistmaker.media.domain.repository.MediaPlaylistsErrorRepository
import com.example.playlistmaker.media.domain.repository.MediaPlaylistsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MediaPlaylistsInteractorImpl(
    val repository: MediaPlaylistsRepository,
    val errorRepository: MediaPlaylistsErrorRepository
): MediaPlaylistsInteractor {

    override fun getPlayLists(): Flow<Pair<Playlists?, ErrorState?>> = flow {
        repository.getPlaylists()
            .collect { playlists ->
                if (playlists.isEmpty()) {
                    emit(Pair(null, ErrorState.Empty(errorRepository.getErrorText())))
                } else {
                    emit(Pair(playlists, null)  )
                }
            }
    }


    override fun add(playlist: Playlist) {
        repository.add(playlist)
    }
}
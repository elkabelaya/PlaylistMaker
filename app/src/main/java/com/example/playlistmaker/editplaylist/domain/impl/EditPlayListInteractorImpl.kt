package com.example.playlistmaker.editplaylist.domain.impl

import android.net.Uri
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.repository.LocalStorageRepository
import com.example.playlistmaker.common.domain.repository.PlaylistsRepository
import com.example.playlistmaker.editplaylist.domain.api.EditPlayListInteractor

class EditPlayListInteractorImpl (
    val playlistsRepository: PlaylistsRepository,
    val storageRepository: LocalStorageRepository,
): EditPlayListInteractor {

    override suspend fun initiate(): Long {
        return playlistsRepository.create()
    }

    override suspend fun delete(playlist: Playlist) {
        playlistsRepository.delete(playlist.id)
        playlist.coverUrl?.let {
            storageRepository.deleteImage(it)
        }
    }

    override fun addCover(uri: Uri?, playlistId: Long): String? {
        uri?.let {
            val savedUri = storageRepository.saveImage(playlistId, uri)
            return savedUri
        }
        return null
    }

    override suspend fun save(playlist: Playlist) {
        playlistsRepository.update(playlist)
    }
}
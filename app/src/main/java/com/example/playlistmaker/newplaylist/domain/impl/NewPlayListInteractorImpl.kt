package com.example.playlistmaker.newplaylist.domain.impl

import android.net.Uri
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.repository.LocalStorageRepository
import com.example.playlistmaker.common.domain.repository.PlaylistsRepository
import com.example.playlistmaker.newplaylist.domain.api.NewPlayListInteractor

class NewPlayListInteractorImpl (
    val playlistsRepository: PlaylistsRepository,
    val storageRepository: LocalStorageRepository,
    var playlist: Playlist = Playlist(name = "")): NewPlayListInteractor {
    var isSaved: Boolean = false

    override suspend fun initiate() {
        val id = playlistsRepository.insert(playlist)
        playlist = playlist.copy(id = id)
    }

    override suspend fun deInitiate() {
        if (!isSaved) {
            playlistsRepository.delete(playlist.id)
            playlist.coverUrl?.let {
                storageRepository.deleteImage(it)
            }
        }
    }

    override fun addCover(uri: Uri?): String? {
        uri?.let {
            val savedUri = storageRepository.saveImage(playlist.id, uri)
            playlist = playlist.copy(coverUrl = savedUri)
            return savedUri
        }

        return null
    }

    override fun updateName(name: String) {
        playlist = playlist.copy(name = name)
    }

    override fun updateDescription(description: String) {
        playlist = playlist.copy(description = description)
    }

    override suspend fun save() {
        isSaved = true
        playlistsRepository.update(playlist)
    }

    override fun hasUnsavedData(): Boolean {
        return playlist.name.isNotEmpty() or
                (playlist.description?.isNotEmpty() == true) or
                (playlist.coverUrl?.isNotEmpty() == true)
    }
}
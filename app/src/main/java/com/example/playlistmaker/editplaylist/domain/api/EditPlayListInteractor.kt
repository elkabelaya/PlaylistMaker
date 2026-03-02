package com.example.playlistmaker.editplaylist.domain.api

import android.net.Uri
import com.example.playlistmaker.common.domain.model.Playlist

interface EditPlayListInteractor {
    suspend fun initiate(): Long

    suspend fun delete(playlist: Playlist)

    fun addCover(uri: Uri?, playlistId: Long): String?
    suspend fun save(playlist: Playlist)
}
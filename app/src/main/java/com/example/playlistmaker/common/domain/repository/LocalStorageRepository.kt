package com.example.playlistmaker.common.domain.repository

import android.net.Uri

enum class LocalStorage(val folder: String) {
    PLAYLIST_COVERS_FOLDER("playlist_covers")
}

interface LocalStorageRepository {
    fun saveImage(id: Long, uri: Uri): String
    fun deleteImage(url: String)
}
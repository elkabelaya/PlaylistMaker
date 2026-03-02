package com.example.playlistmaker.newplaylist.domain.api

import android.net.Uri

interface NewPlayListInteractor {
    suspend fun initiate()

    suspend fun deInitiate()

    fun addCover(uri: Uri?): String?
    fun updateName(name: String)
    fun updateDescription(description: String)
    suspend fun save()
    fun hasUnsavedData(): Boolean
}
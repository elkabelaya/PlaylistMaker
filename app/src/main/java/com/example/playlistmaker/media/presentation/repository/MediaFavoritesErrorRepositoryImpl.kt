package com.example.playlistmaker.media.presentation.repository

import android.content.Context
import com.example.playlistmaker.R
import com.example.playlistmaker.media.domain.repository.MediaFavoritesErrorRepository

class MediaFavoritesErrorRepositoryImpl(val context: Context): MediaFavoritesErrorRepository {
    override fun getErrorText(): String {
        return context.getString(R.string.media_favorites_error_empty)
    }
}
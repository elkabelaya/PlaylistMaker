package com.example.playlistmaker.player.presentation.repository

import android.content.Context
import com.example.playlistmaker.R
import com.example.playlistmaker.player.domain.repository.PlayerDefaultsRepository

class PlayerDefaultsRepositoryImpl(val context: Context): PlayerDefaultsRepository {
    override fun getEmptyTime(): String {
        return context.getString(R.string.player_empty_time)
    }
}
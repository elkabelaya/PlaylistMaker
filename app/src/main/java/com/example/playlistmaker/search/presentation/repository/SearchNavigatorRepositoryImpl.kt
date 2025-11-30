package com.example.playlistmaker.search.presentation.repository

import android.content.Context
import com.example.playlistmaker.common.domain.model.Navigation
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.player.presentation.PlayerActivity
import com.example.playlistmaker.search.domain.repository.SearchNavigatorRepository

class SearchNavigatorRepositoryImpl(): SearchNavigatorRepository {
    override fun getPlayerActivity(item: Track): Navigation {
        return Navigation(PlayerActivity::class.java, PlayerActivity.INTENT_KEY, item)
    }
}
package com.example.playlistmaker.search.presentation.repository

import com.example.playlistmaker.common.domain.model.Navigation
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.player.presentation.PlayerFragment
import com.example.playlistmaker.search.domain.repository.SearchNavigatorRepository

class SearchNavigatorRepositoryImpl(): SearchNavigatorRepository {
    override fun getPlayerActivity(item: Track): Navigation {
        return Navigation(PlayerFragment::class.java, PlayerFragment.INTENT_KEY, item)
    }
}
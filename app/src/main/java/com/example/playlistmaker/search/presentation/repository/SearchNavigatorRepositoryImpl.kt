package com.example.playlistmaker.search.presentation.repository

import android.content.Context
import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.Navigation
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.player.presentation.PlayerFragment
import com.example.playlistmaker.search.domain.repository.SearchNavigatorRepository

class SearchNavigatorRepositoryImpl(): SearchNavigatorRepository {
    init {
        println("SearchNavigatorRepositoryImpl")
    }
    override fun getPlayerActivity(item: Track): Navigation {
        return Navigation(R.id.action_search_playerFragment, PlayerFragment.createArgs(item))
    }
}
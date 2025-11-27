package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.domain.repository.NavigatorRepository
import com.example.playlistmaker.player.presentation.PlayerActivity
import com.example.playlistmaker.search.domain.api.SearchNavigatorInteractor

class SearchNavigatorInteractorImpl(val repository: NavigatorRepository): SearchNavigatorInteractor {
    override fun navigateTo(track: Track) {
        repository.navigateTo(PlayerActivity::class.java, track)
    }
}
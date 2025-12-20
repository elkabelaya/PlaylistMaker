package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.repository.NavigatorRepository
import com.example.playlistmaker.search.domain.api.SearchNavigatorInteractor
import com.example.playlistmaker.search.domain.repository.SearchNavigatorRepository

class SearchNavigatorInteractorImpl(val repository: NavigatorRepository,
    val searchRepository: SearchNavigatorRepository): SearchNavigatorInteractor {
    init {
        println("SearchNavigatorInteractor")
    }
    override fun navigateTo(track: Track) {
        repository.navigateTo(searchRepository.getPlayerActivity(track))
    }
}
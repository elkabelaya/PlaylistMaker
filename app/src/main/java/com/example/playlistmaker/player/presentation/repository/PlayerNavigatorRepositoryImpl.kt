package com.example.playlistmaker.player.presentation.repository

import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.Navigation
import com.example.playlistmaker.newplaylist.presentation.NewPlaylistFragment
import com.example.playlistmaker.player.domain.repository.PlayerNavigatorRepository

class PlayerNavigatorRepositoryImpl(): PlayerNavigatorRepository {

    override fun getNewPlayLst(): Navigation {
        return Navigation(R.id.action_player_newplaylistFragment, NewPlaylistFragment.createArgs())
    }
}
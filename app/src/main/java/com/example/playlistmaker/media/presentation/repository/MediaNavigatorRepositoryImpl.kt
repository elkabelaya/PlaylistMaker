package com.example.playlistmaker.media.presentation.repository

import android.content.Context
import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.Navigation
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.media.domain.repository.MediaNavigatorRepository
import com.example.playlistmaker.player.presentation.PlayerFragment
import com.example.playlistmaker.search.domain.repository.SearchNavigatorRepository

class MediaNavigatorRepositoryImpl(): MediaNavigatorRepository {
    override fun getPlayer(item: Track): Navigation {
        return Navigation(R.id.action_media_playerFragment, PlayerFragment.createArgs(item))
    }
}
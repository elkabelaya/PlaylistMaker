package com.example.playlistmaker.player.domain.repository

import com.example.playlistmaker.common.domain.model.Navigation

interface PlayerNavigatorRepository {
    fun getNewPlayLst(): Navigation
}
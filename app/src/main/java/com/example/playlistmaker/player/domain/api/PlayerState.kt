package com.example.playlistmaker.player.domain.api

import com.example.playlistmaker.common.domain.model.Tracks
import com.example.playlistmaker.search.domain.api.SearchState

sealed class PlayerState {
    data class Default(val time: String): PlayerState()
    data class Prepared(val time: String): PlayerState()
    data class Playing(val time: String): PlayerState()
    data class Paused(val time: String): PlayerState()
}
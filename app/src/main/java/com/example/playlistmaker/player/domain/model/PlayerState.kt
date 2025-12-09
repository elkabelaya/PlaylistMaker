package com.example.playlistmaker.player.domain.model

sealed class PlayerState {
    data class Default(val time: String): PlayerState()
    data class Prepared(val time: String): PlayerState()
    data class Playing(val time: String): PlayerState()
    data class Paused(val time: String): PlayerState()
}
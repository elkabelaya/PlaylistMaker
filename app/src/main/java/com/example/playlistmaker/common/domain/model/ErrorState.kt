package com.example.playlistmaker.common.domain.model

sealed class ErrorState {
    data class Wifi(val text: String): ErrorState()
    data class Empty(val text: String): ErrorState()
}
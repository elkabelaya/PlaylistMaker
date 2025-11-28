package com.example.playlistmaker.common.presentation.error

enum class ErrorType {
    EMPTY,
    WIFI
}

data class ErrorViewModel(var type: ErrorType, var text: String)
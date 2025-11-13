package com.example.playlistmaker.presentation.error

enum class ErrorType {
    EMPTY,
    WIFI
}

data class ErrorViewModel(var type: ErrorType, var text: String)
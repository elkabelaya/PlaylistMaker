package com.example.playlistmaker.error

enum class ErrorType {
    EMPTY,
    WIFI
}

data class ErrorViewModel(var type: ErrorType, var text: String)
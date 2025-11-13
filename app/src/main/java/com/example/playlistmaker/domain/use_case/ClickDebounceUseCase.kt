package com.example.playlistmaker.domain.use_case

interface ClickDebounceUseCase {
    fun canClickDebounced() : Boolean
}
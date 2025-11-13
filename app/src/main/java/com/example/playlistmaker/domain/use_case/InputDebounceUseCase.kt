package com.example.playlistmaker.domain.use_case

interface InputDebounceUseCase {
    fun debounce(callBack:() -> Unit)
}
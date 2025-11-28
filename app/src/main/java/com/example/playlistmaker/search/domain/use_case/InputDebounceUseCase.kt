package com.example.playlistmaker.search.domain.use_case

interface InputDebounceUseCase {
    fun debounce(callBack:() -> Unit)
    fun cancel()
}
package com.example.playlistmaker.domain.impl

import com.example.playlistmaker.domain.repository.LoopRepository
import com.example.playlistmaker.domain.use_case.ClickDebounceUseCase
import com.example.playlistmaker.domain.use_case.InputDebounceUseCase

open class InputDebounceUseCaseImpl(val loopRepository: LoopRepository): InputDebounceUseCase {
   override fun debounce(callBack:() -> Unit) {
        loopRepository.clear()
        loopRepository.post(callBack, INPUT_DEBOUNCE_DELAY)
    }

    companion object {
        private const val INPUT_DEBOUNCE_DELAY = 2000L
    }
}
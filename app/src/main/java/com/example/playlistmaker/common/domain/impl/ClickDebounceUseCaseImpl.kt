package com.example.playlistmaker.common.domain.impl

import com.example.playlistmaker.common.domain.repository.LoopRepository
import com.example.playlistmaker.common.domain.use_case.ClickDebounceUseCase

open class ClickDebounceUseCaseImpl(val loopRepository: LoopRepository): ClickDebounceUseCase {
    private var isClickAllowed: Boolean = true

    override fun canClickDebounced() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            loopRepository.post({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 500L
    }
}
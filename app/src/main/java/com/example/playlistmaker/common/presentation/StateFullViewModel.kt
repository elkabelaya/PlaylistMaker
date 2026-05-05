package com.example.playlistmaker.common.presentation

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.StateFlow

interface StateFullViewModel<T> {
    fun observeState(): LiveData<T>
}

interface ComposeStateFullViewModel<T> {
    var state: StateFlow<T>
}
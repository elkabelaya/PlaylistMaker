package com.example.playlistmaker.common.presentation

import androidx.lifecycle.LiveData

interface StateFullViewModel<T> {
    fun observeState(): LiveData<T>
}
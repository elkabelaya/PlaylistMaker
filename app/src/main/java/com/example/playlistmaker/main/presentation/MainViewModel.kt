package com.example.playlistmaker.main.presentation

import androidx.lifecycle.ViewModel

abstract class MainViewModel: ViewModel() {
    abstract fun search()
    abstract fun media()
    abstract fun settings()
}
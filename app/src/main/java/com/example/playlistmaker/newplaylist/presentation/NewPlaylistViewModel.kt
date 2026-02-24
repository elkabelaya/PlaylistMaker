package com.example.playlistmaker.newplaylist.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.common.presentation.StateFullViewModel
import com.example.playlistmaker.newplaylist.domain.model.NewPlaylistState

abstract class NewPlaylistViewModel: StateFullViewModel<NewPlaylistState>, ViewModel() {
    abstract fun save()
    abstract fun addImage(uri: Uri?)
    abstract fun changeName(name: String)
    abstract fun changeDescription(description: String)
}
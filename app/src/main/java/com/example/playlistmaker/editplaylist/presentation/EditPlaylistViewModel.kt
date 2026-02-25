package com.example.playlistmaker.editplaylist.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.presentation.StateFullViewModel
import com.example.playlistmaker.editplaylist.domain.model.EditPlaylistMode
import com.example.playlistmaker.editplaylist.domain.model.EditPlaylistState

abstract class EditPlaylistViewModel: StateFullViewModel<EditPlaylistState>, ViewModel() {

    abstract fun observeMode(): LiveData<EditPlaylistMode>
    abstract fun observePlayList(): LiveData<Playlist>
    abstract fun observeImage(): LiveData<String?>
    abstract fun save()
    abstract fun addImage(uri: Uri?)
    abstract fun changeName(name: String)
    abstract fun changeDescription(description: String)
}
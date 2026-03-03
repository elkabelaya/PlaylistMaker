package com.example.playlistmaker.playlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Playlists
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.model.Tracks
import com.example.playlistmaker.common.presentation.StateFullViewModel
import com.example.playlistmaker.common.presentation.utils.SingleLiveData
import com.example.playlistmaker.player.domain.model.PlayerState
import com.example.playlistmaker.playlist.domain.model.PlaylistState

abstract class PlaylistViewModel: StateFullViewModel<PlaylistState>, ViewModel() {
    abstract fun observeDialogState(): LiveData<String?>
    abstract fun onShare()
    abstract fun select(track: Track)
    abstract fun delete(track: Track)
    abstract fun onDelete()
    abstract fun onEdit()
}

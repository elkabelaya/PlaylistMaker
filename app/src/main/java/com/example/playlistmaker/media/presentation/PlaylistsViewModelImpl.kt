package com.example.playlistmaker.media.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.playlistmaker.media.domain.model.Playlist
import com.example.playlistmaker.media.domain.model.Playlists

class PlaylistsViewModelImpl: PlaylistsViewModel() {
    private val playlists = MutableLiveData<Playlists>(emptyList())
    override fun observePlayLists(): LiveData<Playlists> = playlists
    override fun select(playlist: Playlist) {
        //do nothing by now
    }
    override fun create(){
        //do nothing by now
    }
}
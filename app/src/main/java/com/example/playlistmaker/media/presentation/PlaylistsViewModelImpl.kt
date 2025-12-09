package com.example.playlistmaker.media.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.playlistmaker.media.domain.api.MediaPlaylistsInteractor
import com.example.playlistmaker.media.domain.model.MediaPlaylistsState
import com.example.playlistmaker.media.domain.model.Playlist
import com.example.playlistmaker.media.domain.model.Playlists

class PlaylistsViewModelImpl(
    val interactor: MediaPlaylistsInteractor
): PlaylistsViewModel() {
    private var stateLiveData = MutableLiveData<MediaPlaylistsState>(MediaPlaylistsState.Loading)
    override fun observeState(): LiveData<MediaPlaylistsState> = stateLiveData

    init {
        interactor.onState {
            stateLiveData.postValue(it)
        }

        interactor.getPlayLists()
    }

    override fun select(playlist: Playlist) {
        //do nothing by now
    }

    override fun create(){
        //do nothing by now
    }
}
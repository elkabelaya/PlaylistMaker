package com.example.playlistmaker.media.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.media.domain.api.MediaNavigatorInteractor
import com.example.playlistmaker.media.domain.api.MediaPlaylistsInteractor
import com.example.playlistmaker.media.domain.model.MediaPlaylistsState
import kotlinx.coroutines.launch

class PlaylistsViewModelImpl(
    val interactor: MediaPlaylistsInteractor,
    val navigatorInteractor: MediaNavigatorInteractor,
): PlaylistsViewModel() {
    private var stateLiveData = MutableLiveData<MediaPlaylistsState>(MediaPlaylistsState.Loading)
    override fun observeState(): LiveData<MediaPlaylistsState> = stateLiveData

    init {
        viewModelScope.launch {
            interactor.getPlayLists()
                .collect { result ->
                    result.first?.let {
                        stateLiveData.postValue(MediaPlaylistsState.Data(it))
                    }
                    result.second?.let {
                        stateLiveData.postValue( MediaPlaylistsState.Error(it))
                    }
                }
        }
    }

    override fun select(playlist: Playlist) {
        //do nothing by now
    }

    override fun create(){
        navigatorInteractor.navigateToNewPlaylist()
    }
}
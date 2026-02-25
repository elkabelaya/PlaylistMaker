package com.example.playlistmaker.media.presentation.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.media.domain.api.MediaNavigatorInteractor
import com.example.playlistmaker.media.domain.api.MediaPlaylistsInteractor
import com.example.playlistmaker.media.domain.model.MediaPlaylistsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MediaPlaylistsViewModelImpl(
    val interactor: MediaPlaylistsInteractor,
    val navigatorInteractor: MediaNavigatorInteractor,
): MediaPlaylistsViewModel() {
    private var stateLiveData = MutableLiveData<MediaPlaylistsState>(MediaPlaylistsState.Loading)
    override fun observeState(): LiveData<MediaPlaylistsState> = stateLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
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
        navigatorInteractor.navigateTo(playlist)
    }

    override fun create(){
        navigatorInteractor.navigateToNewPlaylist()
    }
}
package com.example.playlistmaker.media.presentation.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.media.domain.api.MediaNavigatorInteractor
import com.example.playlistmaker.media.domain.api.MediaPlaylistsInteractor
import com.example.playlistmaker.media.domain.model.MediaPlaylistsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MediaPlaylistsViewModelImpl(
    val interactor: MediaPlaylistsInteractor,
    val navigatorInteractor: MediaNavigatorInteractor,
): MediaPlaylistsViewModel() {
    private val _state = MutableStateFlow<MediaPlaylistsState>(MediaPlaylistsState.Loading)
    override var state: StateFlow<MediaPlaylistsState> = _state.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.getPlayLists()
                .collect { result ->
                    result.first?.let {
                        _state.value = MediaPlaylistsState.Data(it)
                    }
                    result.second?.let {
                        _state.value = MediaPlaylistsState.Error(it)
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
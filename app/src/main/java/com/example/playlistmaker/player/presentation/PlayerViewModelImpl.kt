package com.example.playlistmaker.player.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Playlists
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.presentation.utils.SingleLiveData
import com.example.playlistmaker.player.domain.api.PlayerFavoriteInteractor
import com.example.playlistmaker.player.domain.api.PlayerInteractor
import com.example.playlistmaker.player.domain.api.PlayerNavigatorInteractor
import com.example.playlistmaker.player.domain.api.PlayerPlaylistsInteractor
import com.example.playlistmaker.player.domain.model.PlayerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PlayerViewModelImpl(
    val playerInteractor: PlayerInteractor,
    val favoritesInteractor: PlayerFavoriteInteractor,
    val playlistsInteractor: PlayerPlaylistsInteractor,
    val navigatorInteractor: PlayerNavigatorInteractor,
    private val track: Track?
) : PlayerViewModel() {
    private val playerStateLiveData: MutableLiveData<PlayerState> = MutableLiveData(PlayerState.Default(""))
    override fun observeState(): LiveData<PlayerState> = playerStateLiveData

    private val favoriteStateLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    override fun observeFavorite(): LiveData<Boolean> = favoriteStateLiveData

    private val playlistsLiveData: MutableLiveData<Playlists> = MutableLiveData(emptyList())
    override fun observePlaylists(): LiveData<Playlists> = playlistsLiveData

    private val toastLiveData: SingleLiveData<String?> = SingleLiveData(null)
    override fun observeToast(): SingleLiveData<String?> = toastLiveData

    private var timerJob: Job? = null

    init {
        playerInteractor.setup(track?.previewUrl ?: "")
        playerInteractor.onState{ state ->
            timerJob?.cancel()
            playerStateLiveData.postValue(state)
            if (state is PlayerState.Playing) {
                timerJob = viewModelScope.launch(Dispatchers.IO) {
                    while (true) {
                        delay(PLAYER_DEBOUNCE_DELAY)
                        playerStateLiveData.postValue(PlayerState.Playing(playerInteractor.time()))
                    }
                }
            }
        }
        updateFavorite()
        viewModelScope.launch(Dispatchers.IO) {
            playlistsInteractor.get()
                .collect {
                    playlistsLiveData.postValue(it)
                }
        }
    }

    override fun togglePlay(){
        playerInteractor.togglePlay()
    }

    override fun toggleFavorite() {
        track?.let {
            viewModelScope.launch(Dispatchers.IO) {
                favoritesInteractor.toggle(track)
                updateFavorite()
            }
        }
    }

    override fun createPlaylist() {
        navigatorInteractor.navigateToNewPlaylist()
    }

    override fun select(playlist: Playlist) {
        track?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val result = playlistsInteractor.add(it, playlist)
                toastLiveData.postValue(result)
            }

        }
    }

    override fun onPause() {
        playerInteractor.pause()
    }

    override fun onDestroy() {
        playerInteractor.release()
    }

    private fun updateFavorite() {
        track?.let {
            viewModelScope.launch(Dispatchers.IO) {
                favoriteStateLiveData.postValue(favoritesInteractor.isFavorite(track))
            }
        }
    }
    companion object {
        private const val PLAYER_DEBOUNCE_DELAY = 300L
    }
}
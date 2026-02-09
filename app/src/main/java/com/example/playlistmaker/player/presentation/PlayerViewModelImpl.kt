package com.example.playlistmaker.player.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.player.domain.api.PlayerFavoriteInteractor
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.player.domain.api.PlayerInteractor
import com.example.playlistmaker.player.domain.model.PlayerState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PlayerViewModelImpl(
    val playerInteractor: PlayerInteractor,
    val favoritesInteractor: PlayerFavoriteInteractor,
    private val track: Track?
) : PlayerViewModel() {
    private val playerStateLiveData: MutableLiveData<PlayerState> = MutableLiveData(PlayerState.Default(""))
    override fun observeState(): LiveData<PlayerState> = playerStateLiveData

    private val favoriteStateLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    override fun observeFavorite(): LiveData<Boolean> = favoriteStateLiveData
    private var timerJob: Job? = null

    init {
        playerInteractor.onState{ state ->
            timerJob?.cancel()
            playerStateLiveData.postValue(state)
            if (state is PlayerState.Playing) {
                timerJob = viewModelScope.launch {
                    while (true) {
                        delay(PLAYER_DEBOUNCE_DELAY)
                        playerStateLiveData.postValue(PlayerState.Playing(playerInteractor.time()))
                    }
                }
            }
        }
        updateFavorite()
    }

    override fun setup(url: String?) {
        playerInteractor.setup(url ?: "")
    }

    override fun togglePlay(){
        playerInteractor.togglePlay()
    }

    override fun toggleFavorite() {
        track?.let {
            viewModelScope.launch {
                favoritesInteractor.toggle(track)
                updateFavorite()
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
            viewModelScope.launch {
                favoriteStateLiveData.postValue(favoritesInteractor.isFavorite(track))
            }
        }
    }
    companion object {
        private const val PLAYER_DEBOUNCE_DELAY = 300L
    }
}
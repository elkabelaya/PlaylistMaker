package com.example.playlistmaker.player.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.player.domain.api.PlayerInteractor
import com.example.playlistmaker.player.domain.model.PlayerState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PlayerViewModelImpl(val playerInteractor: PlayerInteractor) : PlayerViewModel() {
    private val playerStateLiveData: MutableLiveData<PlayerState> = MutableLiveData(PlayerState.Default(""))
    override fun observeState(): LiveData<PlayerState> = playerStateLiveData
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

    }

    override fun setup(url: String?) {
        playerInteractor.setup(url ?: "")
    }

    override fun togglePlay(){
        playerInteractor.togglePlay()
    }

    override fun toggleFavorite(){
        //do nothing by now
    }

    override fun onPause() {
        playerInteractor.pause()
    }

    override fun onDestroy() {
        playerInteractor.release()
    }

    companion object {
        private const val PLAYER_DEBOUNCE_DELAY = 300L
    }
}
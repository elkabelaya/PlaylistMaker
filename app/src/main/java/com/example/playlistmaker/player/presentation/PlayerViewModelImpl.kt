package com.example.playlistmaker.player.presentation

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.player.domain.api.PlayerInteractor
import com.example.playlistmaker.player.domain.api.PlayerState


class PlayerViewModelImpl(val playerInteractor: PlayerInteractor) : PlayerViewModel, ViewModel() {
    private val playerStateLiveData: MutableLiveData<PlayerState> = MutableLiveData(PlayerState.Default(""))
    override fun observePlayerState(): LiveData<PlayerState> = playerStateLiveData
    private val handler = Handler(Looper.getMainLooper())

    init {
        playerInteractor.onState{ state ->
            handler.removeCallbacksAndMessages(null)
            playerStateLiveData.postValue(state)
            if (state is PlayerState.Playing) {
                handler.postDelayed(
                    object : Runnable {
                        override fun run() {
                            playerStateLiveData.postValue(PlayerState.Playing(playerInteractor.time()))
                            handler.postDelayed(this, PLAYER_DEBOUNCE_DELAY)
                        }
                    },
                    PLAYER_DEBOUNCE_DELAY
                )
            }
        }

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
        fun getFactory(playerInteractor: PlayerInteractor): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PlayerViewModelImpl(playerInteractor)
            }
        }
        private const val PLAYER_DEBOUNCE_DELAY = 300L
    }
}
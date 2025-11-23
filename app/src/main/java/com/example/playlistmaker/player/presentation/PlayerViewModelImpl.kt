package com.example.playlistmaker.player.presentation

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.player.domain.api.PlayerInteractor
import com.example.playlistmaker.player.domain.api.PlayerState


class PlayerViewModelImpl(private val url: String?) : PlayerViewModel, ViewModel() {
    private val playerStateLiveData = MutableLiveData(PlayerState.STATE_DEFAULT)
    override fun observePlayerState(): LiveData<PlayerState> = playerStateLiveData

    private val progressTimeLiveData = MutableLiveData("00:00")
    override fun observeProgressTime(): LiveData<String> = progressTimeLiveData
    private val favoriveLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    override fun observeFavorite(): LiveData<Boolean> = favoriveLiveData

    private val isFavoriteLiveData = MutableLiveData(false)
    fun observeIsFavourite(): LiveData<Boolean> = isFavoriteLiveData

    private var playerInteractor: PlayerInteractor? = null
    private val handler = Handler(Looper.getMainLooper())

    init {
        url?.let {
            playerInteractor = Creator.providePlayerInteractor(url) { state ->
                handler.removeCallbacksAndMessages(null)
                playerStateLiveData.postValue(state)
                if (state == PlayerState.STATE_PLAYING) {
                    handler.postDelayed(
                        object : Runnable {
                            override fun run() {
                                progressTimeLiveData.postValue(playerInteractor?.time())
                                handler.postDelayed(this, PLAYER_DEBOUNCE_DELAY)
                            }
                        },
                        PLAYER_DEBOUNCE_DELAY
                    )
                }
            }

        }
    }

    override fun togglePlay(){
        playerInteractor?.togglePlay()
    }

    override fun toggleFavorite(){
        favoriveLiveData.postValue(!(favoriveLiveData.value ?: false))
    }

    override fun onPause() {
        playerInteractor?.pause()
    }

    override fun onDestroy() {
        playerInteractor?.release()
    }

    companion object {
        fun getFactory(url: String?): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PlayerViewModelImpl(url)
            }
        }

        const val INTENT_KEY = "INTENT_KEY"
        private const val PLAYER_DEBOUNCE_DELAY = 300L
    }
}
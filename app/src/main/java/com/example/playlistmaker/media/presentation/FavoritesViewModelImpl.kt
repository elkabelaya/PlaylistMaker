package com.example.playlistmaker.media.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.model.Tracks

class FavoritesViewModelImpl: FavoritesViewModel() {
    private val tracks = MutableLiveData<Tracks>(emptyList())
    override fun observeUrl(): LiveData<Tracks> = tracks

    override fun select(track: Track) {
        //do nothing by now
    }
}
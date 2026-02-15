package com.example.playlistmaker.newplaylist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.use_case.ClickDebounceUseCase
import com.example.playlistmaker.media.domain.api.MediaFavoritesInteractor
import com.example.playlistmaker.media.domain.api.MediaNavigatorInteractor
import com.example.playlistmaker.media.domain.model.MediaFavoritesState
import kotlinx.coroutines.launch

class NewPlaylistViewModelImpl(
    val interactor: MediaFavoritesInteractor,
    val clickDebounceUseCase: ClickDebounceUseCase
): NewPlaylistViewModel() {
    private val stateLiveData = MutableLiveData<MediaFavoritesState>(MediaFavoritesState.Loading)
    override fun observeState(): LiveData<MediaFavoritesState> = stateLiveData

    init {
//        viewModelScope.launch {
//            interactor.getFracks()
//                .collect { result ->
//                    result.first?.let {
//                        stateLiveData.postValue( MediaFavoritesState.Data(it))
//                    }
//                    result.second?.let {
//                        stateLiveData.postValue( MediaFavoritesState.Error(it))
//                    }
//                }
//        }
    }
}
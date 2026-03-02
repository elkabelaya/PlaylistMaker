package com.example.playlistmaker.newplaylist.presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.newplaylist.domain.api.NewPlayListInteractor
import com.example.playlistmaker.newplaylist.domain.model.NewPlaylistState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewPlaylistViewModelImpl(

    val interactor: NewPlayListInteractor
): NewPlaylistViewModel() {
    private val stateLiveData = MutableLiveData(NewPlaylistState(false, null, false))
    override fun observeState(): LiveData<NewPlaylistState> = stateLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.initiate()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCleared() {
        super.onCleared()
        GlobalScope.launch(Dispatchers.IO) {
            interactor.deInitiate()
        }
    }

    override fun save() {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.save()
        }
    }

    override fun addImage(uri:Uri?) {
        val savedUrl = interactor.addCover(uri)
        stateLiveData.postValue(stateLiveData.value?.copy (
            imageUrl = savedUrl,
            needDialog = interactor.hasUnsavedData()
        ))
    }

    override fun changeName(name: String) {
        val newName = name.trim()
        interactor.updateName(name)
        stateLiveData.postValue(stateLiveData.value?.copy (
            isSaveEnabled =  !newName.isEmpty(),
            needDialog = interactor.hasUnsavedData()
        ))
    }

    override fun changeDescription(description: String) {
        interactor.updateDescription(description.trim())
        stateLiveData.postValue(stateLiveData.value?.copy (
            needDialog = interactor.hasUnsavedData()
        ))
    }

}
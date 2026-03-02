package com.example.playlistmaker.editplaylist.presentation

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.empty
import com.example.playlistmaker.common.presentation.utils.SingleLiveData
import com.example.playlistmaker.editplaylist.domain.api.EditPlayListInteractor
import com.example.playlistmaker.editplaylist.domain.model.EditPlaylistMode
import com.example.playlistmaker.editplaylist.domain.model.EditPlaylistState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class EditPlaylistViewModelImpl(
    val interactor: EditPlayListInteractor,
     initialPlaylist: Playlist?
): EditPlaylistViewModel() {
    private val stateLiveData: MutableLiveData<EditPlaylistState> = MutableLiveData(EditPlaylistState(initialPlaylist != null, initialPlaylist == null))
    override fun observeState(): LiveData<EditPlaylistState> = stateLiveData

    private val imageLiveData: MutableLiveData<String?> = MutableLiveData(null)
    override fun observeImage(): LiveData<String?> = imageLiveData

    private val modeLiveData = MutableLiveData(if (initialPlaylist == null) EditPlaylistMode.NEW else EditPlaylistMode.EDIT)
    override fun observeMode(): LiveData<EditPlaylistMode> = modeLiveData

    private val playlistLiveData: MutableLiveData<Playlist> = MutableLiveData()
    override fun observePlayList(): LiveData<Playlist> = playlistLiveData

    var isSaved: Boolean = initialPlaylist != null

    init {
        if (initialPlaylist == null) {
            viewModelScope.launch(Dispatchers.IO) {
                val id = interactor.initiate()
                viewModelScope.launch(Dispatchers.Main) {
                    playlistLiveData.value = Playlist.empty().copy(id = id)
                }
            }
        } else {
            playlistLiveData.postValue(initialPlaylist)
            imageLiveData.postValue(initialPlaylist.coverUrl)
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCleared() {
        super.onCleared()
        if (!isSaved) {
            playlistLiveData.value?.let {
                GlobalScope.launch(Dispatchers.IO) {
                    interactor.delete(it)
                }
            }
        }
    }

    override fun save() {
        isSaved = true
        playlistLiveData.value?.let {
            var playlist = it
            viewModelScope.launch(Dispatchers.IO) {
                var savedUrl: String? = null
                imageLiveData.value?.let { imageUrl ->
                    if (imageUrl != playlist.coverUrl) {
                        savedUrl = interactor.addCover(imageUrl.toUri(), playlist.id)
                        playlist = playlist.copy(coverUrl = savedUrl)
                    }
                }
                interactor.save(playlist)
            }
        }
    }

    override fun addImage(uri:Uri?) {
        imageLiveData.postValue(uri.toString())
    }

    override fun changeName(name: String) {
        playlistLiveData.value?.let {
            val newName = name.trim()
            playlistLiveData.postValue(it.copy(name = name.trim()))
            stateLiveData.postValue(
                stateLiveData.value?.copy(
                    isSaveEnabled = !newName.isEmpty(),
                    needDialog = needDialog()
                )
            )
        }
    }

    override fun changeDescription(description: String) {
        playlistLiveData.value?.let {
            playlistLiveData.postValue(it.copy(description = description.trim()))
            stateLiveData.postValue(
                stateLiveData.value?.copy(
                    needDialog = needDialog()
                )
            )
        }
    }

    private fun needDialog(): Boolean {
        return !isSaved and hasAnyData()
    }

    private fun hasAnyData(): Boolean {
        playlistLiveData.value?.let {
            return it.name.isNotEmpty() or
                    (it.description?.isNotEmpty() == true) or
                    (imageLiveData.value?.isNotEmpty() == true)
        }
        return false
    }
}
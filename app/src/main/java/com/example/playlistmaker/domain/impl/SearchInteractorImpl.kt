package com.example.playlistmaker.domain.impl

import com.example.playlistmaker.domain.api.SearchInteractor
import com.example.playlistmaker.domain.api.SearchInteractor.Companion.STATE_DEFAULT
import com.example.playlistmaker.domain.api.SearchInteractor.Companion.STATE_EMPTY
import com.example.playlistmaker.domain.api.SearchInteractor.Companion.STATE_ENTER
import com.example.playlistmaker.domain.api.SearchInteractor.Companion.STATE_ERROR
import com.example.playlistmaker.domain.api.SearchInteractor.Companion.STATE_HISTORY
import com.example.playlistmaker.domain.api.SearchInteractor.Companion.STATE_LOADING
import com.example.playlistmaker.domain.api.SearchInteractor.Companion.STATE_RESULT
import com.example.playlistmaker.domain.consumer.ResourceConsumer
import com.example.playlistmaker.domain.model.Resource
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.domain.model.Tracks
import com.example.playlistmaker.domain.repository.LoopRepository
import com.example.playlistmaker.domain.use_case.GetTracksUseCase
import com.example.playlistmaker.domain.use_case.HistoryUseCase
import com.example.playlistmaker.domain.use_case.InputDebounceUseCase

class SearchInteractorImpl(private val onState:(Int) -> Unit,
                           private val inputDebounceUseCase: InputDebounceUseCase,
                           private val getTracksUseCase: GetTracksUseCase,
                           private val historyUseCase: HistoryUseCase,
                           private val loopRepository: LoopRepository,

) : SearchInteractor {
    override var tracks: Tracks = mutableListOf()
    override var history: Tracks = mutableListOf()
    private var erroredQuery: String? = null
    private var state: Int = STATE_DEFAULT
       set(value) {
           field = value
           loopRepository.clear()
           loopRepository.post({onState(value)}, 0)
       }

    init {
        history = historyUseCase.elements
    }

    override fun changeQuery(query: CharSequence) {
        val trimmedQuery = query.toString().trim()
        if (!trimmedQuery.isEmpty()) {
            inputDebounceUseCase.debounce {
                proceedSearch(trimmedQuery)
            }
            state = STATE_ENTER
        } else {
            inputDebounceUseCase.cancel()
            state = if (isExistsHistory()) STATE_HISTORY else STATE_DEFAULT
        }
    }

    override fun changeFocus(isFocused: Boolean) {
        when (state) {
            STATE_DEFAULT, STATE_HISTORY -> {
                if (isFocused) {
                    state = if (isFocused && isExistsHistory()) STATE_HISTORY else STATE_DEFAULT
                }
            }
        }
    }

    override fun clearQuery() {
        tracks = emptyList()
        state = if (isExistsHistory()) STATE_HISTORY else STATE_DEFAULT
    }

    override fun select(track: Track) {
        historyUseCase.add(track)
        history = historyUseCase.elements
    }

    override fun clearHistory() {
        historyUseCase.clear()
        history = historyUseCase.elements
        state = STATE_DEFAULT
    }

    override fun refresh() {
        erroredQuery?.let {
            proceedSearch(it)
        }
        erroredQuery = null
    }

    private fun isExistsHistory(): Boolean {
        return !history.isEmpty()
    }
    private fun proceedSearch(query: String) {
        state = STATE_LOADING

        getTracksUseCase.getTracks(
            query,
            consumer = object : ResourceConsumer<Tracks> {
                override fun consume(resource: Resource<Tracks>) {
                    when (resource) {
                        is Resource.Success -> {
                            if (!resource.data.isEmpty()) {
                                tracks = resource.data
                                state = STATE_RESULT
                            } else {
                                state = STATE_EMPTY
                            }
                        }

                        is Resource.Error -> {
                            erroredQuery = query
                            state = STATE_ERROR
                        }
                    }
                }
            }
        )
    }
}
package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.common.domain.consumer.ResourceConsumer
import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.common.domain.model.Resource
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.model.Tracks
import com.example.playlistmaker.common.domain.repository.LoopRepository
import com.example.playlistmaker.search.domain.use_case.HistoryUseCase
import com.example.playlistmaker.search.domain.use_case.GetTracksUseCase
import com.example.playlistmaker.search.domain.use_case.InputDebounceUseCase
import com.example.playlistmaker.search.domain.api.SearchInteractor
import com.example.playlistmaker.search.domain.model.SearchState
import com.example.playlistmaker.search.domain.repository.SearchErrorRepository

class SearchInteractorImpl(
    private val inputDebounceUseCase: InputDebounceUseCase,
    private val getTracksUseCase: GetTracksUseCase,
    private val historyUseCase: HistoryUseCase,
    private val loopRepository: LoopRepository,
    private val errorRepository: SearchErrorRepository
    ) : SearchInteractor {
    private var sendState:((SearchState) -> Unit)? = null
    private var erroredQuery: String? = null

    private var state: SearchState = SearchState.Default
       set(value) {
           field = value
           loopRepository.clear()
           loopRepository.post({sendState?.let{it(value)}}, 0)
       }

    override fun onState(state: (SearchState) -> Unit) {
        sendState = state
    }

    override fun changeQuery(query: CharSequence) {
        val trimmedQuery = query.toString().trim()
        if (!trimmedQuery.isEmpty()) {
            inputDebounceUseCase.debounce {
                proceedSearch(trimmedQuery)
            }
            state = SearchState.Enter
        } else {
            inputDebounceUseCase.cancel()
            setDefaultState (true)
        }
    }

    override fun changeFocus(isFocused: Boolean) {
        switchStateIfDefault(isFocused)
    }

    override fun clearQuery() {
        setDefaultState(true)
    }

    override fun select(track: Track) {
        historyUseCase.add(track)
        switchStateIfDefault(true)
    }

    override fun clearHistory() {
        historyUseCase.clear()
        state = SearchState.Default
    }

    override fun refresh() {
        erroredQuery?.let {
            proceedSearch(it)
        }
        erroredQuery = null
    }

    private fun switchStateIfDefault(isFocused: Boolean) {
        when (state) {
            is SearchState.Default, is SearchState.History -> {
                if (isFocused) {
                    setDefaultState (isFocused)
                }
            } else -> {}
        }
    }

    private fun setDefaultState(isFocused: Boolean) {
        state = when {
            isFocused && !historyUseCase.elements.isEmpty() -> SearchState.History(historyUseCase.elements)
            else -> SearchState.Default
        }
    }
    private fun isExistsHistory(): Boolean {
        return !historyUseCase.elements.isEmpty()
    }
    private fun proceedSearch(query: String) {
        state = SearchState.Loading

        getTracksUseCase.getTracks(
            query,
            consumer = object : ResourceConsumer<Tracks> {
                override fun consume(resource: Resource<Tracks>) {
                    when (resource) {
                        is Resource.Success -> {
                            if (!resource.data.isEmpty()) {
                                state = SearchState.Result(resource.data)
                            } else {
                                state = SearchState.Error(ErrorState.Empty(errorRepository.getEmptyText()))
                            }
                        }

                        is Resource.Error -> {
                            erroredQuery = query
                            state = SearchState.Error(ErrorState.Wifi(errorRepository.getWifiText()))
                        }
                    }
                }
            }
        )
    }
}
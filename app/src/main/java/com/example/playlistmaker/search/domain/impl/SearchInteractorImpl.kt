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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SearchInteractorImpl(
    private val inputDebounceUseCase: InputDebounceUseCase,
    private val getTracksUseCase: GetTracksUseCase,
    private val historyUseCase: HistoryUseCase,
    private val loopRepository: LoopRepository,
    private val errorRepository: SearchErrorRepository
    ) : SearchInteractor {
    private var sendState:((SearchState) -> Unit)? = null
    private var lastQuery: String? = null
    private var isFocused: Boolean = false

    private var state: SearchState = SearchState.Default
       set(value) {
           field = value
           loopRepository.clear()
           loopRepository.post({sendState?.let{it(value)}}, 0)
       }

    override fun onState(state: (SearchState) -> Unit) {
        sendState = state
    }

    override suspend fun changeQuery(query: CharSequence) {
        val trimmedQuery = query.toString().trim()
        if (lastQuery != trimmedQuery) {
            lastQuery = trimmedQuery
            if (!trimmedQuery.isEmpty()) {
                state = SearchState.Enter
                suspendCoroutine<Unit> {
                    inputDebounceUseCase.debounce {
                        it.resume(Unit)
                    }
                }
                proceedSearch(trimmedQuery)
            } else {
                inputDebounceUseCase.cancel()
                setDefaultState()
            }
        }
    }

    override fun changeFocus(isFocused: Boolean) {
        this.isFocused = isFocused
        switchStateIfDefault()
    }

    override fun clearQuery() {
        setDefaultState()
    }

    override fun select(track: Track) {
        historyUseCase.add(track)
        switchStateIfDefault()
    }

    override fun clearHistory() {
        historyUseCase.clear()
        state = SearchState.Default
    }

    override suspend fun refresh() {
        lastQuery?.let {
            proceedSearch(it)
        }
    }

    private fun switchStateIfDefault() {
        when (state) {
            is SearchState.Default, is SearchState.History -> {
                setDefaultState ()
            } else -> {}
        }
    }

    private fun setDefaultState() {
        state = when {
            isFocused && !historyUseCase.elements.isEmpty() -> SearchState.History(historyUseCase.elements)
            else -> SearchState.Default
        }
    }
    private fun isExistsHistory(): Boolean {
        return !historyUseCase.elements.isEmpty()
    }
    private suspend fun proceedSearch(query: String) {
        state = SearchState.Loading
            getTracksUseCase
                .getTracks(query)
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            if (!resource.data.isEmpty()) {
                                state = SearchState.Result(resource.data)
                            } else {
                                state =
                                    SearchState.Error(ErrorState.Empty(errorRepository.getEmptyText()))
                            }
                        }

                        is Resource.Error -> {
                            state =
                                SearchState.Error(ErrorState.Wifi(errorRepository.getWifiText()))
                        }
                    }
                }
    }
}
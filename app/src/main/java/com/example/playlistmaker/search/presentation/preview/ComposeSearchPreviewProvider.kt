package com.example.playlistmaker.search.presentation.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.mocks.mockList
import com.example.playlistmaker.search.domain.model.SearchState
import com.example.playlistmaker.search.presentation.SearchViewModel


class ComposeSearchPreviewProvider() : PreviewParameterProvider<SearchViewModel> {
    override val values = sequenceOf(
        SearchViewModelMock(SearchState.Default),
        SearchViewModelMock(SearchState.History(Track.mockList())),
        SearchViewModelMock(SearchState.Enter, "Queen"),
        SearchViewModelMock(SearchState.Loading, "Queen"),
        SearchViewModelMock(SearchState.Result(Track.mockList()), "Queen"),
        SearchViewModelMock(SearchState.Error(ErrorState.Wifi("text")), "Queen"),
    )
}

package com.example.playlistmaker.media.presentation.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.presentation.components.searchbar.mockList
import com.example.playlistmaker.media.domain.model.MediaFavoritesState
import com.example.playlistmaker.media.presentation.favorites.MediaFavoritesViewModel
import com.example.playlistmaker.search.domain.model.SearchState
import com.example.playlistmaker.search.presentation.SearchViewModel
import com.example.playlistmaker.search.presentation.preview.SearchViewModelMock


class ComposeMediaFavoritesPreviewProvider() : PreviewParameterProvider<MediaFavoritesViewModel> {
    override val values = sequenceOf(
        MediaFavoritesViewModelMock(MediaFavoritesState.Loading),
        MediaFavoritesViewModelMock(MediaFavoritesState.Data(Track.mockList())),
        MediaFavoritesViewModelMock(MediaFavoritesState.Error(ErrorState.Empty("Error Text"))),
    )
}

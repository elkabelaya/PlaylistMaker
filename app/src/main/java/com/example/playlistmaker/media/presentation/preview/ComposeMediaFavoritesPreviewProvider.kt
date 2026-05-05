package com.example.playlistmaker.media.presentation.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.mocks.mockList
import com.example.playlistmaker.media.domain.model.MediaFavoritesState
import com.example.playlistmaker.media.presentation.favorites.MediaFavoritesViewModel


class ComposeMediaFavoritesPreviewProvider() : PreviewParameterProvider<MediaFavoritesViewModel> {
    override val values = sequenceOf(
        MediaFavoritesViewModelMock(MediaFavoritesState.Loading),
        MediaFavoritesViewModelMock(MediaFavoritesState.Data(Track.mockList())),
        MediaFavoritesViewModelMock(MediaFavoritesState.Error(ErrorState.Empty("Error Text"))),
    )
}

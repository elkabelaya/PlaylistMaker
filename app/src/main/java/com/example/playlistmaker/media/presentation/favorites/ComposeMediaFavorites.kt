package com.example.playlistmaker.media.presentation.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.example.playlistmaker.R
import com.example.playlistmaker.common.presentation.components.ComposeErrorView
import com.example.playlistmaker.common.presentation.components.track.TrackItemList
import com.example.playlistmaker.media.domain.model.MediaFavoritesState
import com.example.playlistmaker.media.presentation.preview.ComposeMediaFavoritesPreviewProvider
import com.example.playlistmaker.search.presentation.LoadingContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ComposeMediaFavorites(viewModel: MediaFavoritesViewModel = koinViewModel()) {
    val state = viewModel.state.collectAsState().value

    Column(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(R.color.background))
    ) {
        when(state) {
            is MediaFavoritesState.Loading -> {
                LoadingContent()
            }
            is MediaFavoritesState.Data -> {
                TrackItemList(state.tracks) { viewModel.select(it)}
            }
            is MediaFavoritesState.Error -> {
                ComposeErrorView(state.errorState)
            }
        }
    }

}

@Preview
@Composable
fun ComposeMediaFavoritesPreview (
    @PreviewParameter(ComposeMediaFavoritesPreviewProvider::class) model: MediaFavoritesViewModel
) {
    ComposeMediaFavorites(model)
}
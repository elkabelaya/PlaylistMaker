package com.example.playlistmaker.media.presentation.playlists

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.presentation.Dimens
import com.example.playlistmaker.common.presentation.appFontFamily
import com.example.playlistmaker.common.presentation.components.ComposeErrorView
import com.example.playlistmaker.common.presentation.components.buttons.ActionButton
import com.example.playlistmaker.media.domain.model.MediaPlaylistsState
import com.example.playlistmaker.media.presentation.preview.ComposeMediaPlaylistsPreviewProvider
import com.example.playlistmaker.search.presentation.LoadingContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ComposeMediaPlaylists(viewModel: MediaPlaylistsViewModel = koinViewModel()) {
    val state = viewModel.state.collectAsState().value

    Column(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(R.color.background))
    ) {
        ActionButton(
            stringResource(R.string.media_playlist_create),
            viewModel::create
        )
        when (state) {
            is MediaPlaylistsState.Loading -> {
                LoadingContent()
            }

            is MediaPlaylistsState.Data -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(Dimens.paddingS),
                    verticalArrangement = Arrangement.spacedBy(Dimens.paddingXS),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.paddingXS)
                ) {
                    items(state.playlists.count()) { index ->
                        PlaylistCard(
                            playlist = state.playlists[index],
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.select(state.playlists[index]) }
                        )
                    }
                }
            }
            is MediaPlaylistsState.Error -> {
                ComposeErrorView(state.errorState)
            }
        }
    }
}

@Composable
fun PlaylistCard(
    playlist: Playlist,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),

        ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(playlist.coverUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            placeholder = painterResource(R.drawable.bg_placeholder),
            error = painterResource(R.drawable.bg_placeholder),
            fallback = painterResource(R.drawable.bg_placeholder),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(160.dp)
                .clip(RoundedCornerShape(Dimens.radiusM)),

            )


        Spacer(modifier = Modifier.height(Dimens.paddingXXS))

        Text(
            text = playlist.name,
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = Dimens.textS,
            color = colorResource(R.color.text_title),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        Text(
            text = playlist.count,
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = Dimens.textS,
            color = colorResource(R.color.text_title),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ComposeMediaPlaylistsPreview(
    @PreviewParameter(ComposeMediaPlaylistsPreviewProvider::class) model: MediaPlaylistsViewModel
) {
    ComposeMediaPlaylists(model)
}
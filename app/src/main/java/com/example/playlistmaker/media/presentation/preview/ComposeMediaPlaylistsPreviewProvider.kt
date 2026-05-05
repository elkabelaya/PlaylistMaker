package com.example.playlistmaker.media.presentation.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.mocks.mockList
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.media.domain.model.MediaPlaylistsState
import com.example.playlistmaker.media.presentation.playlists.MediaPlaylistsViewModel


class ComposeMediaPlaylistsPreviewProvider() : PreviewParameterProvider<MediaPlaylistsViewModel> {
    override val values = sequenceOf(
        MediaPlaylistsViewModelMock(MediaPlaylistsState.Loading),
        MediaPlaylistsViewModelMock(MediaPlaylistsState.Data(Playlist.mockList())),
        MediaPlaylistsViewModelMock(MediaPlaylistsState.Error(ErrorState.Empty("Error Text"))),
    )
}

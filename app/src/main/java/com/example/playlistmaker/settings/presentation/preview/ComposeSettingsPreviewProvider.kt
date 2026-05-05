package com.example.playlistmaker.settings.presentation.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.presentation.components.searchbar.mockList
import com.example.playlistmaker.search.domain.model.SearchState
import com.example.playlistmaker.search.presentation.preview.SearchViewModelMock
import com.example.playlistmaker.settings.presentation.SettingsViewModel


class ComposeSettingsPreviewProvider() : PreviewParameterProvider<SettingsViewModel> {
    override val values = sequenceOf(
        SettingsViewModelMock(true),
        SettingsViewModelMock(false),
    )
}

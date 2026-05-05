package com.example.playlistmaker.settings.presentation.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.playlistmaker.settings.presentation.SettingsViewModel


class ComposeSettingsPreviewProvider() : PreviewParameterProvider<SettingsViewModel> {
    override val values = sequenceOf(
        SettingsViewModelMock(true),
        SettingsViewModelMock(false),
    )
}

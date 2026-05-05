package com.example.playlistmaker.settings.presentation

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.common.presentation.Dimens
import com.example.playlistmaker.common.presentation.appFontFamily
import com.example.playlistmaker.common.presentation.components.ComposeSwitch
import com.example.playlistmaker.common.presentation.components.TopAppBar
import com.example.playlistmaker.common.presentation.utils.FragmentWithToolBar
import com.example.playlistmaker.databinding.FragmentSettingsBinding
import com.example.playlistmaker.search.presentation.ComposeSearch
import com.example.playlistmaker.search.presentation.SearchViewModel
import com.example.playlistmaker.search.presentation.preview.ComposeSearchPreviewProvider
import com.example.playlistmaker.settings.di.settingsModules
import com.example.playlistmaker.settings.presentation.preview.ComposeSettingsPreviewProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.context.GlobalContext.unloadKoinModules

class SettingsFragment : Fragment() {
    private val viewModel: SettingsViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(settingsModules)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View = ComposeView(requireContext()).apply {
        setContent { ComposeSettings(viewModel) }
    }


    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(settingsModules)
    }
}

@Composable
fun ComposeSettings(viewModel:SettingsViewModel) {
    val isDark by viewModel.isDarkMode.collectAsState()
    Scaffold(
        containerColor = colorResource(R.color.background),
        topBar = {TopAppBar(title = stringResource(id = R.string.main_settings))}
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NightModeSwitch(
                checked = isDark,
                onCheckedChange = {viewModel.switch(it)}
            )
            SettingsButton(
                text = stringResource(id = R.string.settings_share),
                iconRes = R.drawable.ic_share,
                onClick = viewModel::share
            )
            SettingsButton(
                text = stringResource(id = R.string.settings_support),
                iconRes = R.drawable.ic_settings_support,
                onClick = viewModel::support
            )
            SettingsButton(
                text = stringResource(id = R.string.settings_agreement),
                iconRes = R.drawable.ic_arrow_right,
                onClick = viewModel::agreement
            )
        }
    }
}

@Composable
fun NightModeSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.paddingM)
            .height(dimensionResource(id = R.dimen.list_item_height)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.settings_theme),
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize   = Dimens.textM,
            color      = colorResource(id = R.color.text),
            modifier   = Modifier.weight(1f)
        )
        ComposeSwitch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun SettingsButton(
    text: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            contentColor  = colorResource(id = R.color.text)
        ),
        contentPadding = PaddingValues(
            start = 0.dp,
            top = 0.dp,
            end = 0.dp,
            bottom = 0.dp,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.list_item_height))
            .padding(horizontal = Dimens.paddingM)
    ) {
        Text(
            text = text,
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize   = Dimens.textM,
            color = colorResource(R.color.text),
            modifier   = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = colorResource(id = R.color.text_secondary)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ComposeSettingsPreview(
    @PreviewParameter(ComposeSettingsPreviewProvider::class) model: SettingsViewModel
) {
    ComposeSettings(model)
}
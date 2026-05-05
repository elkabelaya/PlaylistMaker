package com.example.playlistmaker.search.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.presentation.Dimens
import com.example.playlistmaker.common.presentation.appFontFamily
import com.example.playlistmaker.common.presentation.components.ComposeErrorView
import com.example.playlistmaker.common.presentation.components.TopAppBar
import com.example.playlistmaker.common.presentation.components.buttons.ActionButton
import com.example.playlistmaker.common.presentation.components.searchbar.SearchBar
import com.example.playlistmaker.common.presentation.components.track.TrackItemList
import com.example.playlistmaker.search.di.searchModules
import com.example.playlistmaker.search.domain.model.SearchState
import com.example.playlistmaker.search.presentation.preview.ComposeSearchPreviewProvider
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.unloadKoinModules
import org.koin.core.context.loadKoinModules

class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(searchModules)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent { ComposeSearch(viewModel) }
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(searchModules)
    }
}


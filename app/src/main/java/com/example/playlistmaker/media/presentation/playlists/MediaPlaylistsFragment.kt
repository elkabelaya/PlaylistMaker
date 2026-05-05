package com.example.playlistmaker.media.presentation.playlists

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.presentation.Dimens
import com.example.playlistmaker.common.presentation.Image
import com.example.playlistmaker.common.presentation.appFontFamily
import com.example.playlistmaker.common.presentation.components.ComposeErrorView
import com.example.playlistmaker.common.presentation.components.buttons.ActionButton
import com.example.playlistmaker.databinding.FragmentMediaPlaylistsBinding
import com.example.playlistmaker.media.domain.model.MediaPlaylistsState
import com.example.playlistmaker.media.presentation.favorites.MediaFavoritesViewModel
import com.example.playlistmaker.media.presentation.preview.ComposeMediaPlaylistsPreviewProvider
import com.example.playlistmaker.search.presentation.LoadingContent
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.viewmodel.koinViewModel


//class MediaPlaylistsFragment : Fragment() {
//    private var _binding: FragmentMediaPlaylistsBinding? = null
//    private val binding get() = _binding!!
//    private val adapter: MediaPlaylistsAdapter
//    private val viewModel: MediaPlaylistsViewModel by viewModel()
//
//    init {
//        adapter = MediaPlaylistsAdapter() { item ->
//            viewModel.select(item)
//        }
//    }
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//
//        _binding = FragmentMediaPlaylistsBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setupViewModel()
//        setupList()
//        setupErrorStub()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//    private fun setupViewModel() {
//        viewModel.observeState().observe(viewLifecycleOwner) { state ->
//            when (state) {
//                is MediaPlaylistsState.Loading -> {
//
//                }
//                is MediaPlaylistsState.Error -> {
//                    binding.error.setState(state.errorState)
//                    binding.error.isVisible = true
//                    adapter.playlists = emptyList()
//                    adapter.notifyDataSetChanged()
//                }
//                is MediaPlaylistsState.Data -> {
//                    binding.error.isVisible = false
//                    adapter.playlists = state.playlists
//                    adapter.notifyDataSetChanged()
//                }
//            }
//        }
//    }
//
//    private fun setupList() {
//        binding.playlists.layoutManager = GridLayoutManager(requireContext(), 2)
//        binding.playlists.adapter = adapter
//    }
//    private fun setupErrorStub() {
//        binding.createButton.setOnClickListener {
//            viewModel.create()
//        }
//    }
//
//    companion object {
//        @JvmStatic
//        fun newInstance() =
//            MediaPlaylistsFragment()
//    }
//}

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
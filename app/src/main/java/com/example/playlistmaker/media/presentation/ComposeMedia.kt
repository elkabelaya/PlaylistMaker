package com.example.playlistmaker.media.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.playlistmaker.R
import com.example.playlistmaker.common.presentation.Dimens
import com.example.playlistmaker.common.presentation.appFontFamily
import com.example.playlistmaker.common.presentation.components.TopAppBar
import com.example.playlistmaker.media.presentation.favorites.ComposeMediaFavorites
import com.example.playlistmaker.media.presentation.playlists.ComposeMediaPlaylists
import kotlinx.coroutines.launch

@Composable
fun ComposeMedia() {
    val tabTitles = listOf(R.string.media_tab_favorites, R.string.media_tab_playlists)
    val pagerState = rememberPagerState(pageCount = { 2 })
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = colorResource(R.color.background),
        topBar = {TopAppBar(title = stringResource(id = R.string.main_media))}
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                divider = {},
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                            .padding(horizontal = Dimens.paddingM)
                            .height(2.dp),
                        color = colorResource(id = R.color.tab_content)
                    )
                },
                containerColor = colorResource(id = R.color.background),
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(text = stringResource(title),
                            fontFamily = appFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = Dimens.textXM,
                            color = colorResource(R.color.tab_content)
                        ) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
            ) { page ->
                when (page) {
                    0 -> ComposeMediaFavorites()
                    1 -> ComposeMediaPlaylists()
                }
            }
        }
    }
}

@Preview
@Composable
fun ComposeMediaPreview() {
    ComposeMedia()
}
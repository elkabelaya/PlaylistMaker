package com.example.playlistmaker.common.presentation.components.track

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.presentation.Dimens
import com.example.playlistmaker.common.domain.mocks.mockList

@Composable
fun TrackItemList(items: List<Track>, onClick: (Track) -> Unit) {
    LazyColumn(modifier = Modifier.padding(top = Dimens.paddingM)) {
        items(items) { item ->
            TrackItem(item, onClick)
        }
    }
}

@Preview
@Composable
fun TrackItemListPreview() {
    TrackItemList(Track.mockList()){}
}
package com.example.playlistmaker.common.presentation.components.track

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.playlistmaker.common.domain.model.Track

@Composable
fun TrackItemList(items: List<Track>, onClick: (Track) -> Unit) {
    LazyColumn {
        items(items) { item ->
            TrackItem(item, onClick)
        }
    }
}
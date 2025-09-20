package com.example.playlistmaker.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.models.Track

class TracksAdapter (val onClickItem: (Track) -> Unit) : RecyclerView.Adapter<TrackViewHolder> () {
    var tracks: List<Track> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        return TrackViewHolder(parent)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position], onClickItem)
    }

    override fun getItemCount() = tracks.size
}
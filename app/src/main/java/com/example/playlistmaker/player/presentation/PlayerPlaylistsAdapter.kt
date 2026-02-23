package com.example.playlistmaker.player.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Playlists

class PlayerPlaylistsAdapter(val onClickItem: (Playlist) -> Unit) : RecyclerView.Adapter<PlayerPlaylistViewHolder> () {
        var playlists: Playlists = emptyList()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerPlaylistViewHolder {
            return PlayerPlaylistViewHolder.from(parent)
        }

        override fun onBindViewHolder(holder: PlayerPlaylistViewHolder, position: Int) {
            holder.bind(playlists[position], onClickItem)
        }

        override fun getItemCount() = playlists.size

}
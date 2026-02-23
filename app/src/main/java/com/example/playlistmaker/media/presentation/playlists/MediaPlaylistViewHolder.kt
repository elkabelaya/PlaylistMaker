package com.example.playlistmaker.media.presentation.playlists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.databinding.MediaPlaylistCardViewBinding

class MediaPlaylistViewHolder(private val binding: MediaPlaylistCardViewBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): MediaPlaylistViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = MediaPlaylistCardViewBinding.inflate(inflater, parent, false)
            return MediaPlaylistViewHolder(binding)
        }
    }

    fun bind(model: Playlist, onClickItem: (Playlist) -> Unit) {
        binding.name.text = model.name.trim()
        binding.count.text = model.count
        Glide.with( binding.image)
            .load(model.coverUrl)
            .placeholder(R.drawable.bg_placeholder)
            .centerCrop()
            .transform(RoundedCorners( binding.image.resources.getDimensionPixelSize(R.dimen.radius_xs)))
            .into( binding.image)

        itemView.setOnClickListener {
            onClickItem(model)
        }
    }
}
package com.example.playlistmaker.player.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.databinding.PlayerPlaylistsCardViewBinding

class PlayerPlaylistViewHolder(private val binding: PlayerPlaylistsCardViewBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): PlayerPlaylistViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = PlayerPlaylistsCardViewBinding.inflate(inflater, parent, false)
            return PlayerPlaylistViewHolder(binding)
        }
    }

    fun bind(model: Playlist, onClickItem: (Playlist) -> Unit) {
        binding.title.text = model.name
        binding.tracksCount.text = model.count
        Glide.with( binding.image)
            .load(model.coverUrl)
            .placeholder(R.drawable.bg_placeholder)
            .centerCrop()
            .transform(RoundedCorners( binding.image.resources.getDimensionPixelSize(R.dimen.radius_xxxs)))
            .into( binding.image)

        itemView.setOnClickListener {
            onClickItem(model)
        }
    }
}
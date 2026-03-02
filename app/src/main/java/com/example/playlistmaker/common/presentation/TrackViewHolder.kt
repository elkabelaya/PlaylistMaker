package com.example.playlistmaker.common.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.TrackViewBinding
import com.example.playlistmaker.common.domain.model.Track

class TrackViewHolder(private val binding: TrackViewBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): TrackViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = TrackViewBinding.inflate(inflater, parent, false)
            return TrackViewHolder(binding)
        }
    }

    fun bind(model: Track, onClickItem: (Track) -> Unit) {
        binding.titleView.text = model.trackName.trim()
        binding.artistView.text = model.artistName?.trim()
        binding.timeView.text = model.trackTime

        binding.dotView.isVisible =  binding.artistView.text.isNotEmpty() and  binding.timeView.text.isNotEmpty()

        Glide.with( binding.imageView)
            .load(model.imageUrl)
            .placeholder(R.drawable.bg_placeholder)
            .centerCrop()
            .transform(RoundedCorners( binding.imageView.resources.getDimensionPixelSize(R.dimen.radius_xxxs)))
            .into( binding.imageView)

        itemView.setOnClickListener {
            onClickItem(model)
        }
    }
}
package com.example.playlistmaker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TrackViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(
        R.layout.track_view,
        parent,
        false)
) {

    private val titleView: TextView = itemView.findViewById(R.id.title)
    private val artistView: TextView = itemView.findViewById(R.id.artist)
    private val timeView: TextView = itemView.findViewById(R.id.time)
    private val imageView: ImageView = itemView.findViewById(R.id.image)
    private val dotView: ImageView = itemView.findViewById(R.id.dot)

    fun bind(model: Track) {
        titleView.text = model.trackName
        artistView.text = model.artistName
        timeView.text = model.trackTime

        dotView.isVisible = model.trackName.isNotEmpty() and model.trackTime.isNotEmpty()

        Glide.with(imageView)
            .load(model.artworkUrl100)
            .placeholder(R.drawable.bg_placeholder)
            .centerCrop()
            .transform(RoundedCorners(imageView.resources.getDimensionPixelSize(R.dimen.radius_xxxs)))
            .into(imageView)

    }
}
package com.example.playlistmaker.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

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

    fun bind(model: Track, onClickItem: (Track) -> Unit) {
        titleView.text = model.trackName.trim()
        artistView.text = model.artistName?.trim()
        timeView.text = model.formattedTrackTime

        dotView.isVisible = artistView.text.isNotEmpty() and timeView.text.isNotEmpty()

        Glide.with(imageView)
            .load(model.imageUrl)
            .placeholder(R.drawable.bg_placeholder)
            .centerCrop()
            .transform(RoundedCorners(imageView.resources.getDimensionPixelSize(R.dimen.radius_xxxs)))
            .into(imageView)

        itemView.setOnClickListener {
            onClickItem(model)
        }
    }
}
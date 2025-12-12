package com.example.playlistmaker.common.presentation.error

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.databinding.ErrorViewBinding

class ErrorView(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {
    private lateinit var binding: ErrorViewBinding
    init {
        binding = ErrorViewBinding.inflate(LayoutInflater.from(context), this, true)
    }
    fun setState(state: ErrorState) {
        when (state) {
            is ErrorState.Empty -> {
                binding.icon.setImageResource(R.drawable.ic_error_empty)
                binding.text.text = state.text
            }
            is ErrorState.Wifi -> {
                binding.icon.setImageResource(R.drawable.ic_error_wifi)
                binding.text.text = state.text
            }
        }
    }
}
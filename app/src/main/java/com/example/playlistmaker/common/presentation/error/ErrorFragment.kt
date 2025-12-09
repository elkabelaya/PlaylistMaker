package com.example.playlistmaker.common.presentation.error

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.databinding.FragmentErrorBinding
import com.example.playlistmaker.databinding.TrackViewBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ErrorFragment : Fragment() {
    private lateinit var binding: FragmentErrorBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentErrorBinding.inflate(inflater, container, false)
        return binding.root
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

    companion object {
        @JvmStatic
        fun newInstance() =
            ErrorFragment()
    }
}
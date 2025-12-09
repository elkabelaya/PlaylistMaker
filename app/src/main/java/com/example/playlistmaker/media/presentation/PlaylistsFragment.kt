package com.example.playlistmaker.media.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.playlistmaker.common.presentation.error.ErrorFragment
import com.example.playlistmaker.databinding.FragmentPlaylistsBinding
import com.example.playlistmaker.media.domain.model.MediaPlaylistsState
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class PlaylistsFragment : Fragment() {
    private lateinit var  binding: FragmentPlaylistsBinding
    val viewModel: PlaylistsViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupErrorStub()
    }

    private fun setupViewModel() {
        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is MediaPlaylistsState.Error -> {
                    binding.errorFragment.getFragment<ErrorFragment>().setState(state.errorState)
                    binding.errorFragment.isVisible = true
                }
                else -> {
                    binding.errorFragment.isVisible = false
                    //do nothing by now
                }
            }
        }
    }

    private fun setupErrorStub() {
        binding.createButton.setOnClickListener {
            viewModel.create()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PlaylistsFragment()
    }
}
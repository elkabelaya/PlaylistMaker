package com.example.playlistmaker.media.presentation.playlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.playlistmaker.databinding.FragmentMediaPlaylistsBinding
import com.example.playlistmaker.media.domain.model.MediaPlaylistsState
import org.koin.androidx.viewmodel.ext.android.viewModel


class MediaPlaylistsFragment : Fragment() {
    private var _binding: FragmentMediaPlaylistsBinding? = null
    private val binding get() = _binding!!
    private val adapter: MediaPlaylistsAdapter
    private val viewModel: MediaPlaylistsViewModel by viewModel()

    init {
        adapter = MediaPlaylistsAdapter() { item ->
            viewModel.select(item)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMediaPlaylistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupList()
        setupErrorStub()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setupViewModel() {
        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is MediaPlaylistsState.Loading -> {

                }
                is MediaPlaylistsState.Error -> {
                    binding.error.setState(state.errorState)
                    binding.error.isVisible = true
                }
                is MediaPlaylistsState.Data -> {
                    binding.error.isVisible = false
                    adapter.playlists = state.playlists
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun setupList() {
        binding.playlists.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.playlists.adapter = adapter
    }
    private fun setupErrorStub() {
        binding.createButton.setOnClickListener {
            viewModel.create()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MediaPlaylistsFragment()
    }
}
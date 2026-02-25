package com.example.playlistmaker.media.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.common.presentation.TracksAdapter
import com.example.playlistmaker.databinding.FragmentMediaFavoritesBinding
import com.example.playlistmaker.media.domain.model.MediaFavoritesState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MediaFavoritesFragment : Fragment() {

    private var _binding: FragmentMediaFavoritesBinding? = null
    private val binding get() = _binding!!
    private val adapter: TracksAdapter
    private val viewModel: MediaFavoritesViewModel by viewModel()

    init {
        adapter = TracksAdapter({ item ->
            viewModel.select(item)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMediaFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViewModel() {
        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is MediaFavoritesState.Loading -> {
                    binding.error.isVisible = false
                    binding.tracksList.isVisible = false
                    binding.progressbar.root.isVisible = true
                }
                is MediaFavoritesState.Error -> {
                    binding.error.isVisible = true
                    binding.tracksList.isVisible = false
                    binding.progressbar.root.isVisible = false
                    binding.error.setState(state.errorState)

                }
                is MediaFavoritesState.Data -> {
                    adapter.tracks = state.tracks
                    adapter.notifyDataSetChanged()
                    binding.error.isVisible = false
                    binding.tracksList.isVisible = true
                    binding.progressbar.root.isVisible = false
                }
            }
        }
    }

    private fun setupList() {
        binding.tracksList.layoutManager = LinearLayoutManager(requireContext())
        binding.tracksList.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MediaFavoritesFragment()
    }

}
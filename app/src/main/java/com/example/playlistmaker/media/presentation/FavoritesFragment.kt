package com.example.playlistmaker.media.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.playlistmaker.common.presentation.error.ErrorView
import com.example.playlistmaker.databinding.FragmentFavoritesBinding
import com.example.playlistmaker.media.domain.model.MediaFavoritesState
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    val viewModel: FavoritesViewModel  by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViewModel() {
        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is MediaFavoritesState.Error -> {
                    binding.error.isVisible = true
                    binding.error.setState(state.errorState)
                    //do nothing by now
                }
            else -> {
                binding.error.isVisible = false
                //do nothing by now
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FavoritesFragment()
    }

}
package com.example.playlistmaker.search.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.common.presentation.utils.FragmentWithToolBar
import com.example.playlistmaker.common.presentation.utils.hideKeyboardFrom
import com.example.playlistmaker.databinding.FragmentSearchBinding
import com.example.playlistmaker.search.di.searchModules
import com.example.playlistmaker.search.domain.model.SearchState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.unloadKoinModules
import org.koin.core.context.loadKoinModules
import org.koin.core.parameter.parametersOf
import kotlin.getValue

class SearchFragment : FragmentWithToolBar() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val adapter: TracksAdapter
    private val historyAdapter: TracksAdapter

    private val navHost by lazy { this.findNavController() }
    private val viewModel: SearchViewModel by viewModel(){parametersOf(navHost) }

    init {
        adapter = TracksAdapter() {
            item -> viewModel.select(item)
        }
        historyAdapter = TracksAdapter() { item -> viewModel.select(item)
//val navigation = Navigation1(R.id.action_global_playerFragment, PlayerFragment.INTENT_KEY, item)
//                    findNavController().navigate(
//            navigation.action,
//            Bundle().apply {
//                putSerializable(navigation.key, navigation.data)
//            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(searchModules)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolBar(getResources().getString(R.string.main_search), false, binding.toolbar)

        setupViewModel()
        setupSearchBar()
        setupList()
        setupErrorStub()
        setupHistoryGroup()
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(searchModules)
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//    }

//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//    }

    private fun setupViewModel() {
        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is SearchState.Default -> {
                    binding.searchbar.resetButton.isVisible = false
                    binding.historyGroup.isVisible = false
                    binding.progressbar.root.isVisible = false
                    binding.tracksList.isVisible = false
                   hideError()
                }
                is SearchState.History -> {
                    binding.historyList.layoutManager?.scrollToPosition(0)
                    historyAdapter.tracks = state.tracks
                    historyAdapter.notifyDataSetChanged()
                    binding.searchbar.resetButton.isVisible = false
                    binding.historyGroup.isVisible = true
                    binding.progressbar.root.isVisible = false
                    binding.tracksList.isVisible = false
                    hideError()
                }
                is SearchState.Enter -> {
                    binding.searchbar.resetButton.isVisible = true
                    binding.historyGroup.isVisible = false
                    binding.progressbar.root.isVisible = false
                    binding.tracksList.isVisible = false
                    hideError()
                }
                is SearchState.Loading -> {
                    binding.searchbar.resetButton.isVisible = true
                    binding.historyGroup.isVisible = false
                    binding.progressbar.root.isVisible = true
                    binding.tracksList.isVisible = false
                    hideError()
                }
                is SearchState.Result -> {
                    binding.tracksList.layoutManager?.scrollToPosition(0)
                    adapter.tracks = state.tracks
                    adapter.notifyDataSetChanged()
                    binding.searchbar.resetButton.isVisible = true
                    binding.historyGroup.isVisible = false
                    binding.progressbar.root.isVisible = false
                    binding.tracksList.isVisible = true
                    hideError()
                }
                is SearchState.Error -> {
                    binding.searchbar.resetButton.isVisible = true
                    binding.historyGroup.isVisible = false
                    binding.progressbar.root.isVisible = false
                    showError(state.errorState)
                }
            }
        }
    }
    private fun setupSearchBar() {
        binding.searchbar.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.changeQuery(s)
            }

            override fun afterTextChanged(s: Editable) {}
        })

        binding.searchbar.editText.setOnFocusChangeListener { view, hasFocus ->
            viewModel.changeFocus(hasFocus)
        }

        binding.searchbar.resetButton.setOnClickListener {
            viewModel.clearQuery()
            binding.searchbar.editText.setText("")
            hideKeyboardFrom(requireContext(), binding.searchbar.editText)
        }
    }
    private fun setupList() {
        binding.tracksList.layoutManager = LinearLayoutManager(requireContext())
        binding.tracksList.adapter = adapter
    }

    private fun setupErrorStub() {
        binding.refreshButton.setOnClickListener {
            viewModel.refresh()
        }
    }
    private fun setupHistoryGroup() {
        binding.historyList.layoutManager = LinearLayoutManager(requireContext())
        binding.historyList.adapter = historyAdapter

        binding.clearButton.setOnClickListener {
            viewModel.clearHistory()
        }
    }

    private fun showError(state: ErrorState) {
        binding.error.setState(state)
        binding.error.isVisible = true
        binding.refreshButton.isVisible = state is ErrorState.Wifi
    }

    private fun hideError() {
        binding.error.isVisible = false
        binding.refreshButton.isVisible = false
    }
}
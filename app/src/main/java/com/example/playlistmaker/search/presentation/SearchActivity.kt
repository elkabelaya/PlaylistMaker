package com.example.playlistmaker.search.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.common.presentation.utils.AppCompatActivityWithToolBar
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.common.presentation.error.ErrorType
import com.example.playlistmaker.common.presentation.error.ErrorViewModel
import com.example.playlistmaker.common.presentation.utils.hideKeyboardFrom
import com.example.playlistmaker.search.domain.api.SearchState

class SearchActivity : AppCompatActivityWithToolBar() {
    private lateinit var binding: ActivitySearchBinding
    private val adapter: TracksAdapter
    private val historyAdapter: TracksAdapter
    lateinit var viewModel: SearchViewModel

    init {

        adapter = TracksAdapter() { item -> viewModel.select(item) }
        historyAdapter = TracksAdapter() { item -> viewModel.select(item) }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolBar(getResources().getString(R.string.main_search), binding.root, binding.toolbar)

        setupViewModel()
        setupSearchBar()
        setupList()
        setupErrorStub()
        setupHistoryGroup()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, SearchViewModelImpl.getFactory(this ))
            .get(SearchViewModelImpl::class.java)
        viewModel.observeState().observe(this) { state ->
            when (state) {
                is SearchState.Default -> {
                    binding.searchbar.resetButton.isVisible = false
                    binding.historyGroup.isVisible = false
                    binding.progressbar.root.isVisible = false
                    binding.tracksList.isVisible = false
                    hideError()
                }
                is SearchState.History -> {
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
                    adapter.tracks = state.tracks
                    adapter.notifyDataSetChanged()
                    binding.searchbar.resetButton.isVisible = true
                    binding.historyGroup.isVisible = false
                    binding.progressbar.root.isVisible = false
                    binding.tracksList.isVisible = true
                    hideError()
                }
                is SearchState.Empty -> {
                    binding.searchbar.resetButton.isVisible = true
                    binding.historyGroup.isVisible = false
                    binding.progressbar.root.isVisible = false
                    showError(ErrorType.EMPTY, R.string.error_not_found)
                }
                is SearchState.Error -> {
                    binding.searchbar.resetButton.isVisible = true
                    binding.historyGroup.isVisible = false
                    binding.progressbar.root.isVisible = false
                    showError(ErrorType.WIFI, R.string.error_wifi)
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
            hideKeyboardFrom(this, binding.searchbar.editText)
        }
    }
    private fun setupList() {
        binding.tracksList.layoutManager = LinearLayoutManager(this)
        binding.tracksList.adapter = adapter
    }

    private fun setupErrorStub() {
        binding.refreshButton.setOnClickListener {
            viewModel.refresh()
        }
    }
    private fun setupHistoryGroup() {
        binding.historyList.layoutManager = LinearLayoutManager(this)
        binding.historyList.adapter = historyAdapter

        binding.clearButton.setOnClickListener {
            viewModel.clearHistory()
        }
    }

    private fun showError(type: ErrorType, stringId: Int) {
        binding.errorStub.errorViewModel = ErrorViewModel(type, getResources().getString(stringId))
        binding.refreshButton.isVisible = type == ErrorType.WIFI
    }

    private fun hideError() {
        binding.errorStub.errorViewModel = null
        binding.refreshButton.isVisible = false
    }
}
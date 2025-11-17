package com.example.playlistmaker.presentation.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.presentation.PlayerActivity
import com.example.playlistmaker.presentation.utils.AppCompatActivityWithToolBar
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.presentation.error.ErrorType
import com.example.playlistmaker.presentation.error.ErrorViewModel
import com.example.playlistmaker.presentation.utils.hideKeyboardFrom
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.databinding.ActivityMediaBinding
import com.example.playlistmaker.domain.api.SearchInteractor
import com.example.playlistmaker.domain.api.SearchInteractor.Companion.STATE_DEFAULT
import com.example.playlistmaker.domain.api.SearchInteractor.Companion.STATE_EMPTY
import com.example.playlistmaker.domain.api.SearchInteractor.Companion.STATE_ENTER
import com.example.playlistmaker.domain.api.SearchInteractor.Companion.STATE_ERROR
import com.example.playlistmaker.domain.api.SearchInteractor.Companion.STATE_HISTORY
import com.example.playlistmaker.domain.api.SearchInteractor.Companion.STATE_LOADING
import com.example.playlistmaker.domain.api.SearchInteractor.Companion.STATE_RESULT

class SearchActivity : AppCompatActivityWithToolBar() {
    private lateinit var binding: ActivitySearchBinding
    private val adapter: TracksAdapter
    private val historyAdapter: TracksAdapter
    lateinit var searchInteractor: SearchInteractor

    init {
        adapter = TracksAdapter() { item -> onClickItem(item) }
        historyAdapter = TracksAdapter() { item -> onClickItem(item) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolBar(getResources().getString(R.string.main_search), binding.root, binding.toolbar)

        searchInteractor = Creator.provideSearchInteractor(this){ state ->
            when (state) {
                STATE_DEFAULT -> {
                    binding.searchbar.resetButton.isVisible = false
                    binding.historyGroup.isVisible = false
                    binding.progressbar.root.isVisible = false
                    binding.tracksList.isVisible = false
                    hideError()
                }
                STATE_HISTORY -> {
                    binding.searchbar.resetButton.isVisible = false
                    binding.historyGroup.isVisible = true
                    binding.progressbar.root.isVisible = false
                    binding.tracksList.isVisible = false
                    hideError()
                }
                STATE_ENTER -> {
                    binding.searchbar.resetButton.isVisible = true
                    binding.historyGroup.isVisible = false
                    binding.progressbar.root.isVisible = false
                    binding.tracksList.isVisible = false
                    hideError()
                }
                STATE_LOADING -> {
                    binding.searchbar.resetButton.isVisible = true
                    binding.historyGroup.isVisible = false
                    binding.progressbar.root.isVisible = true
                    binding.tracksList.isVisible = false
                    hideError()
                }
                STATE_RESULT -> {
                    binding.searchbar.resetButton.isVisible = true
                    binding.historyGroup.isVisible = false
                    binding.progressbar.root.isVisible = false
                    binding.tracksList.isVisible = true
                    adapter.tracks = searchInteractor.tracks
                    adapter.notifyDataSetChanged()
                    hideError()
                }
                STATE_EMPTY -> {
                    binding.searchbar.resetButton.isVisible = true
                    binding.historyGroup.isVisible = false
                    binding.progressbar.root.isVisible = false
                    showError(ErrorType.EMPTY, R.string.error_not_found)
                }
                STATE_ERROR -> {
                    binding.searchbar.resetButton.isVisible = true
                    binding.historyGroup.isVisible = false
                    binding.progressbar.root.isVisible = false
                    showError(ErrorType.WIFI, R.string.error_wifi)
                }
            }
        }
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

    private fun setupSearchBar() {
        binding.searchbar.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                searchInteractor.changeQuery(s)
            }

            override fun afterTextChanged(s: Editable) {}
        })

        binding.searchbar.editText.setOnFocusChangeListener { view, hasFocus ->
            searchInteractor.changeFocus(hasFocus)
        }

        binding.searchbar.resetButton.setOnClickListener {
            searchInteractor.clearQuery()
            binding.searchbar.editText.setText("")
            hideKeyboardFrom(this, binding.searchbar.editText)
        }
    }
    private fun setupList() {
        adapter.tracks = searchInteractor.tracks
        binding.tracksList.layoutManager = LinearLayoutManager(this)
        binding.tracksList.adapter = adapter
    }

    private fun setupErrorStub() {
        binding.refreshButton.setOnClickListener {
            searchInteractor.refresh()
        }
    }
    private fun setupHistoryGroup() {
        historyAdapter.tracks = searchInteractor.history
        binding.historyList.layoutManager = LinearLayoutManager(this)
        binding.historyList.adapter = historyAdapter

        binding.clearButton.setOnClickListener {
            searchInteractor.clearHistory()
            historyAdapter.notifyDataSetChanged()
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

    private fun onClickItem(item: Track) {
        if (canClickDebounced()) {
            searchInteractor.select(item)
            historyAdapter.notifyDataSetChanged()
            val displayIntent = Intent(this, PlayerActivity::class.java)
            displayIntent.putExtra(PlayerActivity.INTENT_KEY, item)
            startActivity(displayIntent)
        }
    }
}
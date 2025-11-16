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
    private lateinit var editText: EditText
    private lateinit var resetButton: ImageView
    private lateinit var tracksList: RecyclerView
    private lateinit var historyGroup: ViewGroup
    private lateinit var progressbar: ProgressBar
    lateinit var searchInteractor: SearchInteractor

    init {
        adapter = TracksAdapter() { item -> onClickItem(item) }
        historyAdapter = TracksAdapter() { item -> onClickItem(item) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchInteractor = Creator.provideSearchInteractor(this){ state ->
            when (state) {
                STATE_DEFAULT -> {
                    resetButton.isVisible = false
                    historyGroup.isVisible = false
                    progressbar.isVisible = false
                    tracksList.isVisible = false
                    hideError()
                }
                STATE_HISTORY -> {
                    resetButton.isVisible = false
                    historyGroup.isVisible = true
                    progressbar.isVisible = false
                    tracksList.isVisible = false
                    hideError()
                }
                STATE_ENTER -> {
                    resetButton.isVisible = true
                    historyGroup.isVisible = false
                    progressbar.isVisible = false
                    tracksList.isVisible = false
                    hideError()
                }
                STATE_LOADING -> {
                    resetButton.isVisible = true
                    historyGroup.isVisible = false
                    progressbar.isVisible = true
                    tracksList.isVisible = false
                    hideError()
                }
                STATE_RESULT -> {
                    resetButton.isVisible = true
                    historyGroup.isVisible = false
                    progressbar.isVisible = false
                    tracksList.isVisible = true
                    adapter.tracks = searchInteractor.tracks
                    adapter.notifyDataSetChanged()
                    hideError()
                }
                STATE_EMPTY -> {
                    resetButton.isVisible = true
                    historyGroup.isVisible = false
                    progressbar.isVisible = false
                    showError(ErrorType.EMPTY, R.string.error_not_found)
                }
                STATE_ERROR -> {
                    resetButton.isVisible = true
                    historyGroup.isVisible = false
                    progressbar.isVisible = false
                    showError(ErrorType.WIFI, R.string.error_wifi)
                }
            }
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        editText = findViewById<EditText>(R.id.search_text)
        resetButton = findViewById<ImageView>(R.id.reset_button)
        historyGroup = findViewById<ViewGroup>(R.id.history_group)
        progressbar = findViewById<ProgressBar>(R.id.progressbar)
        setupToolBar(getResources().getString(R.string.main_search))
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
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                searchInteractor.changeQuery(s)
            }

            override fun afterTextChanged(s: Editable) {}
        })

        editText.setOnFocusChangeListener { view, hasFocus ->
            searchInteractor.changeFocus(hasFocus)
        }

        resetButton.setOnClickListener {
            searchInteractor.clearQuery()
            editText.setText("")
            hideKeyboardFrom(this, editText)
        }
    }
    private fun setupList() {
        adapter.tracks = searchInteractor.tracks
        tracksList = findViewById<RecyclerView>(R.id.list)
        tracksList.layoutManager = LinearLayoutManager(this)
        tracksList.adapter = adapter
    }

    private fun setupErrorStub() {
        val refreshButton = findViewById<Button>(R.id.refresh_button)

        refreshButton.setOnClickListener {
            searchInteractor.refresh()
        }
    }
    private fun setupHistoryGroup() {
        historyAdapter.tracks = searchInteractor.history
        val historyList = findViewById<RecyclerView>(R.id.history)
        historyList.layoutManager = LinearLayoutManager(this)
        historyList.adapter = historyAdapter

        val clearButton = findViewById<Button>(R.id.clear_button)
        clearButton.setOnClickListener {
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
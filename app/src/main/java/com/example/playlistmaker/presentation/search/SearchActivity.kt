package com.example.playlistmaker.presentation.search

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.example.playlistmaker.domain.model.Tracks
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.presentation.error.ErrorType
import com.example.playlistmaker.presentation.error.ErrorViewModel
import com.example.playlistmaker.presentation.utils.hideKeyboardFrom
import com.example.playlistmaker.presentation.utils.InputDebouncer
import retrofit2.Call
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.domain.api.HistoryInteractor
import com.example.playlistmaker.domain.consumer.ResourceConsumer
import com.example.playlistmaker.domain.model.Resource

class SearchActivity : AppCompatActivityWithToolBar() {
    private lateinit var binding: ActivitySearchBinding
    private var tracks: MutableList<Track> = mutableListOf()
    private val adapter: TracksAdapter
    private val historyAdapter: TracksAdapter
    private lateinit var editText: EditText
    private lateinit var resetButton: ImageView
    private lateinit var historyGroup: ViewGroup
    private lateinit var historyInteractor: HistoryInteractor
    private var searchCall: Call<Tracks>? = null
    private val handler = Handler(Looper.getMainLooper())
    private var tracksRunnable: Runnable? = null

    private val searchDebouncer = InputDebouncer()
    val tracksInteractor = Creator.provideTracksInteractor()

    init {
        adapter = TracksAdapter() { item -> onClickItem(item) }
        historyAdapter = TracksAdapter() { item -> onClickItem(item) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyInteractor = Creator.provideHistoryInteractor(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        editText = findViewById<EditText>(R.id.search_text)
        resetButton = findViewById<ImageView>(R.id.reset_button)
        historyGroup = findViewById<ViewGroup>(R.id.history_group)
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
               resetButton.isVisible = s.isNotEmpty()
                changeHistoryGroup(true)
                searchDebounced()
            }

            override fun afterTextChanged(s: Editable) {}
        })

        editText.setOnFocusChangeListener { view, hasFocus ->
            changeHistoryGroup(hasFocus)
        }

        resetButton.setOnClickListener{
            clearInput()
        }
    }
    private fun setupList() {
        adapter.tracks = tracks
        val tracksList = findViewById<RecyclerView>(R.id.list)
        tracksList.layoutManager = LinearLayoutManager(this)
        tracksList.adapter = adapter
    }

    private fun setupErrorStub() {
        val refreshButton = findViewById<Button>(R.id.refresh_button)

        refreshButton.setOnClickListener {
            proceedSearch()
        }
    }
    private fun setupHistoryGroup() {
        historyAdapter.tracks = historyInteractor.elements
        val historyList = findViewById<RecyclerView>(R.id.history)
        historyList.layoutManager = LinearLayoutManager(this)
        historyList.adapter = historyAdapter

        val clearButton = findViewById<Button>(R.id.clear_button)
        clearButton.setOnClickListener {
            historyInteractor.clear()
            historyAdapter.notifyDataSetChanged()
            changeHistoryGroup(false)
        }
    }

    private fun changeHistoryGroup(hasFocus: Boolean) {
        historyGroup.isVisible = hasFocus and editText.getText().isEmpty() and historyInteractor.elements.isNotEmpty()
    }
    private fun clearInput() {
        editText.setText("")
        hideError()
        tracks.clear()
        hideKeyboardFrom(this, editText)
        changeHistoryGroup(true)
    }

    private fun proceedSearch() {
        val input = editText.text.toString().trim()

        hideError()

        tracks.clear()

        if (input.isEmpty()) {
            clearInput()
        } else {
            fillList(input)
        }
    }

    private fun searchDebounced() {
        searchDebouncer.debounce {
            proceedSearch()
        }
    }
    private fun fillList(query: String) {
        toggleLoader(true)
        changeHistoryGroup(true)

        tracksInteractor.getTracks(
            query,
            consumer = object : ResourceConsumer<Tracks> {
                override fun consume(resource: Resource<Tracks>) {
                    val currentRunnable = tracksRunnable
                    if (currentRunnable != null) {
                        handler.removeCallbacks(currentRunnable)
                    }

                    val newRunnable = Runnable {
                        toggleLoader(false)
                        when (resource) {
                            is Resource.Success -> {
                                if (!resource.data.isEmpty()) {
                                    tracks.addAll(resource.data)
                                    adapter.notifyDataSetChanged()
                                } else {
                                    showError(ErrorType.EMPTY, R.string.error_not_found)
                                }
                            }

                            is Resource.Error -> {
                                showError(ErrorType.WIFI, R.string.error_wifi)
                            }
                        }
                    }
                    tracksRunnable = newRunnable
                    handler.post(newRunnable)
                }
            })
    }

    private fun toggleLoader(show: Boolean) {
        val progressbar = findViewById<ProgressBar>(R.id.progressbar)
        progressbar.isVisible = show
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
            historyInteractor.add(item)
            historyAdapter.notifyDataSetChanged()
            val displayIntent = Intent(this, PlayerActivity::class.java)
            displayIntent.putExtra(PlayerActivity.INTENT_KEY, item)
            startActivity(displayIntent)
        }
    }

    companion object {
        const val TEXT_KEY = "TEXT_KEY"
        const val TEXT_DEF = ""
    }
}
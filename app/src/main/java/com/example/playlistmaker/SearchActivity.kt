package com.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.ActivityMainBinding
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.error.ErrorType
import com.example.playlistmaker.error.ErrorViewModel
import com.example.playlistmaker.network.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchActivity : AppCompatActivityWithToolBar() {
    private lateinit var binding: ActivitySearchBinding
    private var searchText: String = TEXT_DEF
    private lateinit var tracksList: RecyclerView
    private var tracks: MutableList<Track> = mutableListOf()
    private val adapter = TracksAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        hideError()
        setupToolBar(getResources().getString(R.string.main_search))
        setupSearchBar()
        setupList()
        setupErrorStub()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TEXT_KEY, searchText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        searchText = savedInstanceState.getString(TEXT_KEY, TEXT_DEF)
        val editText = findViewById<EditText>(R.id.search_text)
        editText.setText(searchText)
    }

    private fun setupSearchBar() {
        val editText = findViewById<EditText>(R.id.search_text)
        val resetButton = findViewById<ImageView>(R.id.reset_button)

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val currentText = s.toString()
                resetButton.isVisible = s.isNotEmpty()
                searchText = currentText
            }

            override fun afterTextChanged(s: Editable) {}
        })

        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                fillList()
                true
            }
            false
        }

        resetButton.setOnClickListener{
            editText.setText("")
            hideKeyboardFrom(this, editText)
        }
    }

    private fun setupList() {
        adapter.tracks = tracks
        tracksList = findViewById<RecyclerView>(R.id.list)
        tracksList.layoutManager = LinearLayoutManager(this)
        tracksList.adapter = adapter
    }

    private fun setupErrorStub() {
        val refreshButton = findViewById<Button>(R.id.refresh_button)

        refreshButton.setOnClickListener {
            fillList()
        }
    }

    private fun fillList() {
        hideError()
        Network.itunesService().findTrack(searchText).enqueue(object : Callback<Tracks> {
            override fun onResponse(
                call: Call<Tracks?>,
                response: Response<Tracks?>
            ) {
                tracks.clear()
                val results = response.body()?.results?.filterNotNull()
                if (results?.isNotEmpty() == true) {
                    tracks.addAll(results)
                    adapter.notifyDataSetChanged()
                } else {
                    showError(ErrorType.EMPTY, R.string.error_not_found)
                }
            }

            override fun onFailure(
                call: Call<Tracks?>,
                t: Throwable
            ) {
                showError(ErrorType.WIFI, R.string.error_wifi)
            }

        })
    }

    private fun showError(type: ErrorType, stringId: Int) {
        binding.errorStub.errorViewModel = ErrorViewModel(type, getResources().getString(stringId))
        binding.refreshButton.isVisible = type == ErrorType.WIFI
    }

    private fun hideError() {
        binding.errorStub.errorViewModel = null
        binding.refreshButton.isVisible = false
    }

    companion object {
        const val TEXT_KEY = "TEXT_KEY"
        const val TEXT_DEF = ""
    }
}
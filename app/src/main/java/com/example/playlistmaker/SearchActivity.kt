package com.example.playlistmaker
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class SearchActivity : AppCompatActivityWithToolBar() {
    var searchText: String = TEXT_DEF
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupToolBar(getResources().getString(R.string.main_search))
        setupSearchBar()

        fillList()
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

        resetButton.setOnClickListener{
            editText.setText("")
            hideKeyboardFrom(this, editText)
        }
    }

    private fun fillList() {
        val recycler = findViewById<RecyclerView>(R.id.commentsList)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = TracksAdapter(
            generateTracksMock()
        )
    }

    companion object {
        const val TEXT_KEY = "TEXT_KEY"
        const val TEXT_DEF = ""
    }
}
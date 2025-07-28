package com.example.playlistmaker

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar


open class AppCompatActivityWithToolBar: AppCompatActivity() {
    fun setupToolBar(title: String) {
        val toolbar = findViewById<MaterialToolbar>(R.id.tool_bar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = title

        //Для кнопки «Назад» экрана «Настройки» добавить обработчик нажатий.
        //Добавить код перехода на главный экран в обработчик.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Handle the Up button click here
                onBackPressed() // This will simulate a back press
                return true
            }

            else -> return false
            // else -> return super.onOptionsItemSelected(item)
        }
    }
}
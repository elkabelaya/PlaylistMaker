package com.example.playlistmaker

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar


open class AppCompatActivityWithToolBar: AppCompatActivity() {
    fun setupToolBar(title: String) {
        val toolbar = findViewById<MaterialToolbar>(R.id.tool_bar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = title

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                //Для кнопки «Назад» экрана «Настройки» добавить обработчик нажатий.
                //Добавить код перехода на главный экран в обработчик.

                onBackPressed()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }
}
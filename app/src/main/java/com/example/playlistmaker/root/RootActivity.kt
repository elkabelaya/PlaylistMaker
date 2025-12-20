package com.example.playlistmaker.root

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.common.presentation.utils.setupTopInset
import com.example.playlistmaker.databinding.ActivityRootBinding
import com.example.playlistmaker.media.presentation.MediaFragment
import com.example.playlistmaker.player.di.playerModules
import com.example.playlistmaker.search.presentation.SearchFragment
import com.example.playlistmaker.settings.presentation.SettingsFragment
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.androidx.scope.ScopeActivity
import org.koin.core.context.loadKoinModules
import com.example.playlistmaker.root.di.rootModule
import org.koin.core.context.GlobalContext.unloadKoinModules

class RootActivity : ScopeActivity() {
    private lateinit var binding: ActivityRootBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(rootModule)
        enableEdgeToEdge()
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTopInset(this, binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.root_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.search_fragment, R.id.settings_fragment, R.id.media_fragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                else -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(rootModule)
    }
}
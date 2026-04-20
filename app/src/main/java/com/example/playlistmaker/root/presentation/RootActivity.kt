package com.example.playlistmaker.root.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.repository.Permission
import com.example.playlistmaker.common.presentation.repository.NavControllerKeeper
import com.example.playlistmaker.databinding.ActivityRootBinding
import com.example.playlistmaker.root.di.rootModule
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext
import org.koin.core.context.loadKoinModules

class RootActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRootBinding
    private val navKeeper: NavControllerKeeper by inject()
    private val viewModel: RootViewModel by viewModel()
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        viewModel.onNotification(isGranted)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadKoinModules(rootModule)
        enableEdgeToEdge()
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            viewModel.onNotification(true)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onNotification(
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        )

    }
    override fun onDestroy() {
        super.onDestroy()
        GlobalContext.unloadKoinModules(rootModule)
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.root_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)
        navKeeper.setup(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.search_fragment, R.id.settings_fragment, R.id.media_fragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                else -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
            }
            binding.divider.visibility = binding.bottomNavigation.visibility
        }
    }
}
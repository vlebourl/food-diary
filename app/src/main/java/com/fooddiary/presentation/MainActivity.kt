package com.fooddiary.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.fooddiary.R
import com.fooddiary.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.navView

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        // Define top-level destinations (no up button shown)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_timeline,
                R.id.navigation_entry,
                R.id.navigation_analysis,
                R.id.navigation_reports,
                R.id.navigation_settings
            )
        )

        // Setup action bar with navigation controller
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Setup bottom navigation with navigation controller
        navView.setupWithNavController(navController)

        // Handle navigation item selection
        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_timeline -> {
                    navController.navigate(R.id.navigation_timeline)
                    true
                }
                R.id.navigation_entry -> {
                    navController.navigate(R.id.navigation_entry)
                    true
                }
                R.id.navigation_analysis -> {
                    navController.navigate(R.id.navigation_analysis)
                    true
                }
                R.id.navigation_reports -> {
                    navController.navigate(R.id.navigation_reports)
                    true
                }
                R.id.navigation_settings -> {
                    navController.navigate(R.id.navigation_settings)
                    true
                }
                else -> false
            }
        }

        // Set up navigation listener to update selected item
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_timeline -> navView.selectedItemId = R.id.navigation_timeline
                R.id.navigation_entry -> navView.selectedItemId = R.id.navigation_entry
                R.id.navigation_analysis -> navView.selectedItemId = R.id.navigation_analysis
                R.id.navigation_reports -> navView.selectedItemId = R.id.navigation_reports
                R.id.navigation_settings -> navView.selectedItemId = R.id.navigation_settings
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
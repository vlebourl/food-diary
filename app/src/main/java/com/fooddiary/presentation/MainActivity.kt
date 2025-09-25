package com.fooddiary.presentation

import android.os.Bundle
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.fooddiary.R

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_timeline,
                R.id.navigation_entry,
                R.id.navigation_analysis,
                R.id.navigation_reports,
                R.id.navigation_settings,
            ),
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        navView.setupWithNavController(navController)

        val quickEntryFab = findViewById<ExtendedFloatingActionButton>(R.id.fab_quick_entry)
        quickEntryFab.setOnClickListener {
            navController.navigate(R.id.navigation_entry)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}

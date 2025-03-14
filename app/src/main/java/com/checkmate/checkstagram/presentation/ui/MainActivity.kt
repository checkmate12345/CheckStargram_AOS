package com.checkmate.checkstagram.presentation.ui

import android.os.Bundle
import androidx.core.view.isGone
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.checkmate.checkstagram.R
import com.checkmate.checkstagram.databinding.ActivityMainBinding
import com.checkmate.checkstagram.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    private lateinit var navController: NavController
    private val destinationChangedListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            setBottomNavigationVisibility(destination)
        }

    private val exceptBottomNavigationSet = listOf(
        R.id.loginFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBottomNavigation()
    }

    override fun onDestroy() {
        super.onDestroy()
        navController.removeOnDestinationChangedListener(destinationChangedListener)
    }

    private fun setUpBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view_main) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationViewMain.setupWithNavController(navController)
        navController.addOnDestinationChangedListener(destinationChangedListener)
    }

    private fun setBottomNavigationVisibility(destination: NavDestination) {
        binding.bottomNavigationViewMain.isGone = exceptBottomNavigationSet.contains(destination.id)
    }
}
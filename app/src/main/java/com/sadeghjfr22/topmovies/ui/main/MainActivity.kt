package com.sadeghjfr22.topmovies.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.sadeghjfr22.topmovies.R
import com.sadeghjfr22.topmovies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavController()

        binding.navigationView.setupWithNavController(navController)

        binding.btnMenu.setOnClickListener { binding.drawerLayout.openDrawer(GravityCompat.START) }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }

    private fun setupNavController(){

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        navController = navHostFragment!!.navController

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == R.id.homeFragment) setMenuButtonVisibility(true)

            else setMenuButtonVisibility(false)
        }
    }

    private fun setMenuButtonVisibility(visibility: Boolean){

        binding.btnMenu.isVisible = visibility
    }

    override fun onBackPressed() {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))

            binding.drawerLayout.closeDrawer(GravityCompat.START)

        else super.onBackPressed()
    }
}
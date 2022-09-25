package com.example.week4assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.week4assignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()
        val navHost = supportFragmentManager.findFragmentById(R.id.container_fragment) as
                NavHostFragment
        val navController = navHost.navController
        val appConfig = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appConfig)
        binding.menuToolbar.setupWithNavController(navController)


    }
}
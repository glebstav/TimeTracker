package com.example.timetracker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.timetracker.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var userLogin: String;
    override fun onCreate(savedInstanceState: Bundle?) {
        userLogin = intent.getStringExtra("userLogin").toString()

        if (savedInstanceState != null) {

            savedInstanceState.putString("userLogin", userLogin)
            super.onCreate(savedInstanceState)
        } else {
            val b = bundleOf(
                Pair("userLogin", userLogin),
            )
            Toast.makeText(this, "Ваш login = '$userLogin'", Toast.LENGTH_SHORT).show()
            super.onCreate(b)
        }


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }
}
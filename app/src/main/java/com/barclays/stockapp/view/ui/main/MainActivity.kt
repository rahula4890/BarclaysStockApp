package com.barclays.stockapp.view.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.barclays.stockapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.nav_host_fragment_container)
        toolbar = supportActionBar!!
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setupWithNavController(
            navController
        )
    }
}
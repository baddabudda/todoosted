package com.forgblord.todo_prototype

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.forgblord.todo_prototype.databinding.ActivityMainBinding
import com.forgblord.todo_prototype.fragments.add_task.AddTaskDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.inbox, R.id.today, R.id.track, R.id.browse))
        setupActionBarWithNavController(navController, appBarConfiguration)

        val bottomNavBar: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavBar.setupWithNavController(navController)

        bottomNavBar.setOnItemReselectedListener { item ->
            navController.popBackStack()
        }

        binding.fabAddTask.setOnClickListener {
            navController.navigate(R.id.add_task)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    /*private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            navController.popBackStack()
        }
    }*/

    fun getAddButton(): FloatingActionButton {
        return binding.fabAddTask
    }
}
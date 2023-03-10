package com.example.home

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.home.databinding.ActivityHomePage1Binding
import com.example.home.fragments.HomeFragment
import com.example.home.fragments.ProfileFragment
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePage1 : AppCompatActivity() {

    private lateinit var binding: ActivityHomePage1Binding
    private val homeFragment = HomeFragment()
    private val profileFragment = ProfileFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePage1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigation.selectedItemId = R.id.homeButton


        supportFragmentManager.beginTransaction()
            .replace(R.id.container, homeFragment)
            .commit()

        binding.bottomNavigation.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.homeButton -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, homeFragment)
                            .commit()
                        return true
                    }
                    R.id.profileButton -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, profileFragment)
                            .commit()
                        return true
                    }
                }
                return false
            }
        })
    }
}
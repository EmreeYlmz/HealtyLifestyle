package com.example.diyetisyenuyg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.diyetisyenuyg.databinding.ActivityAnagrisBinding
import com.example.diyetisyenuyg.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.newFixedThreadPoolContext

class Anagris : AppCompatActivity() {
//    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityAnagrisBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityAnagrisBinding.inflate(layoutInflater)
        setContentView(binding.root)
      //  auth= FirebaseAuth.getInstance()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val navController = Navigation.findNavController(this, R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)


    }
}



package com.rafalropel.passwordworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rafalropel.passwordworld.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddValuesActivity::class.java)
            startActivity(intent)
        }
    }
}
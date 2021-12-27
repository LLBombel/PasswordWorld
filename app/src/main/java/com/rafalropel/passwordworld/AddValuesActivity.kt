package com.rafalropel.passwordworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rafalropel.passwordworld.databinding.ActivityAddValuesBinding
import com.rafalropel.passwordworld.databinding.ActivityMainBinding

private lateinit var binding: ActivityAddValuesBinding

class AddValuesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddValuesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
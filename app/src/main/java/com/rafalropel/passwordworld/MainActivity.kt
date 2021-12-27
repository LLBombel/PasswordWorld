package com.rafalropel.passwordworld

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafalropel.passwordworld.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private lateinit var binding: ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val passwordDao = (application as PasswordAPP).db.passwordDao()
        binding.btnAdd.setOnClickListener {
            addRecord(passwordDao)
        }


        lifecycleScope.launch{
            passwordDao.fetchAllData().collect {
                val list = ArrayList(it)
                displayData(list, passwordDao)
            }
        }

    }

    private fun addRecord(passwordDAO: PasswordDAO){
        val name = binding.etName.text.toString()
        val login = binding.etLogin.text.toString()
        val password = binding.etPassword.text.toString()

        if(name.isNotEmpty() && login.isNotEmpty() && password.isNotEmpty()){
            lifecycleScope.launch{
                passwordDAO.insert(PasswordEntity(name = name, login= login, password = password))
                Toast.makeText(applicationContext, "Zapisano", Toast.LENGTH_SHORT).show()
                binding.etName.text?.clear()
                binding.etLogin.text?.clear()
                binding.etPassword.text?.clear()
            }
        }else{
            Toast.makeText(this, "Proszę wypełnić wszystkie pola", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayData(passwordList: ArrayList<PasswordEntity>, passwordDao : PasswordDAO){
        if(passwordList.isNotEmpty()){
            val passwordAdapter = PasswordAdapter(passwordList)
            binding.rvPasswordList.layoutManager = LinearLayoutManager(this)
            binding.rvPasswordList.adapter = passwordAdapter
        }


    }
}
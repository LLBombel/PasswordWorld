package com.rafalropel.passwordworld

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafalropel.passwordworld.databinding.ActivityMainBinding
import com.rafalropel.passwordworld.databinding.DialogEditBinding
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

    private fun displayData(passwordList: ArrayList<PasswordEntity>, passwordDao: PasswordDAO){
        if(passwordList.isNotEmpty()){
            val passwordAdapter = PasswordAdapter(passwordList, {
                updateId ->
                updateData(updateId, passwordDao)
            },{
                deleteId ->
                deleteData(deleteId, passwordDao)
            }

                )
            binding.rvPasswordList.layoutManager = LinearLayoutManager(this)
            binding.rvPasswordList.adapter = passwordAdapter
        }


    }

    private fun updateData(id: Int, passwordDAO: PasswordDAO){
        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)
        val binding = DialogEditBinding.inflate(layoutInflater)
        updateDialog.setContentView(binding.root)

        lifecycleScope.launch {
            passwordDAO.fetchDataById(id).collect {
                binding.etUpdateName.setText(it.name)
                binding.etUpdateLogin.setText(it.login)
                binding.etUpdatePassword.setText(it.password)
            }
        }

            binding.tvUpdate.setOnClickListener {
                val name = binding.etUpdateName.text.toString()
                val login = binding.etUpdateLogin.text.toString()
                val password = binding.etUpdatePassword.text.toString()

                if(name.isNotEmpty() && login.isNotEmpty() && password.isNotEmpty()){
                    lifecycleScope.launch{
                        passwordDAO.update(PasswordEntity(id, name, login, password))
                        Toast.makeText(applicationContext, "Zaktualizowano pozycję", Toast.LENGTH_SHORT).show()
                        updateDialog.dismiss()
                    }
            }else{
                Toast.makeText(applicationContext, "Proszę wypełnić wszystkie pola", Toast.LENGTH_SHORT).show()
                }
        }

        binding.tvCancel.setOnClickListener {
            updateDialog.dismiss()
        }

        updateDialog.show()
    }




    private fun deleteData(id: Int, passwordDao: PasswordDAO){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Usuń pozycję - czy na pewno?")
        builder.setIcon(R.drawable.ic_alert)
        builder.setPositiveButton("Tak"){dialogInterface, _->
            lifecycleScope.launch{
                passwordDao.delete(PasswordEntity(id))
                Toast.makeText(applicationContext, "Usunięto pozycję",
                Toast.LENGTH_SHORT).show()

            }
            dialogInterface.dismiss()
        }

        builder.setNegativeButton("Nie"){dialogInterface, _ ->
            dialogInterface.dismiss()
        }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
    }
}
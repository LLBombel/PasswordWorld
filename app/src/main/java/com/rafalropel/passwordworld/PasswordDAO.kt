package com.rafalropel.passwordworld

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface PasswordDAO {

@Insert
suspend fun insert(passwordEntity: PasswordEntity)

@Update
suspend fun update(passwordEntity: PasswordEntity)

@Delete
suspend fun delete(passwordEntity: PasswordEntity)


@Query("SELECT * FROM `passwords-table`")
fun fetchAllData():Flow<List<PasswordEntity>>

@Query("SELECT * FROM  `passwords-table` where id=:id"  )
fun fetchDataById(id: Int):Flow<List<PasswordEntity>>

}
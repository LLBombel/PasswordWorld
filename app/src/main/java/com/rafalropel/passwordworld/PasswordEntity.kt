package com.rafalropel.passwordworld

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "passwords-table")
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,


    var name: String = "",
    var login: String = "",
    var password: String = ""
)



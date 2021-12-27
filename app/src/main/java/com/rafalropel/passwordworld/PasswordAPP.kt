package com.rafalropel.passwordworld

import android.app.Application

class PasswordAPP:Application() {
    val db by lazy {
        PasswordDatabase.getInstance(this)
    }
}
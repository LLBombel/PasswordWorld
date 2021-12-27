package com.rafalropel.passwordworld

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [PasswordEntity::class], version = 1)
abstract class PasswordDatabase: RoomDatabase() {

    abstract fun passwordDao(): PasswordDAO


    companion object{
        @Volatile
        private var INSTANCE: PasswordDatabase? = null

        fun getInstance(context: Context):PasswordDatabase{

            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PasswordDatabase::class.java,
                        "password_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }




}
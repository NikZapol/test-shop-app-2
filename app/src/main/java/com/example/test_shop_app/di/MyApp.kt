package com.example.loginregister.database
import android.app.Application
import androidx.room.Room
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    companion object {
        lateinit var database: AppDatabase
    }
    override fun onCreate() {
        super.onCreate()

        // create the database instance
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my-database"
        ).build()
    }
}
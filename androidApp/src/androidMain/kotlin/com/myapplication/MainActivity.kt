package com.myapplication

import MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.initialize
import goaldetails.di.goalDetails
import home.di.goalModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import statistic.di.statistic
import tasks.di.platformModule
import tasks.di.tasksModule

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Firebase.initialize(this)
        startKoin {
            androidContext(this@MainActivity)
            modules(goalModule() + tasksModule() + goalDetails() + statistic() + platformModule)
        }
        setContent {
            MainView()
        }
    }
}
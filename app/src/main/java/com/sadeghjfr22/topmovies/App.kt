package com.sadeghjfr22.topmovies

import android.app.Application

class App : Application() {

    companion object{

        private lateinit var sInstance: App

        private fun init(app: App){
            sInstance = app
        }

        fun getInstance(): App{
            return sInstance
        }
    }

    override fun onCreate() {
        super.onCreate()

        init(this)
    }

}

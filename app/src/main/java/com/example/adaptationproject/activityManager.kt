package com.example.adaptationproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

class activityManager(private var activityCon: Context) {
    fun openMapActivity(){
        val intent = Intent(activityCon, MapsActivity::class.java)
        startActivity(intent)
    }
}
package com.example.adaptationproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.adaptationproject.ui.theme.AdaptationProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user_id = intent.getStringExtra("id").toString()
        Log.d("----------------------------", "-")
        Log.d("Id: ", user_id)
        Log.d("----------------------------", "-")
        setContent {
            AdaptationProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    //color = MaterialTheme.colors.background
                ) {
                    val context = LocalContext.current
                    HomeScreen(context, user_id)
                }
            }
        }
    }
}
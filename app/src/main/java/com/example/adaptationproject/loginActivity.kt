package com.example.adaptationproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adaptationproject.ui.theme.AdaptationProjectTheme

class loginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdaptationProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val context = LocalContext.current
                    LoginScreen(context)
                }
            }
        }
    }
}

@Composable
fun LoginScreen(context: Context) {
    val login = remember{ mutableStateOf("") }
    val password = remember{ mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val activity = (context as? Activity)
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center){
        TextField(value = login.value, onValueChange = {login.value = it}, modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(15.dp)
            .heightIn(25.dp, 55.dp))
        TextField(value = password.value, onValueChange = {password.value = it}, modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(15.dp)
            .heightIn(25.dp, 55.dp),
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
            if (showPassword) {
                IconButton(onClick = { showPassword = false }, modifier = Modifier
                    .sizeIn(25.dp,25.dp,55.dp,55.dp)) {
                    Icon(painter = painterResource(R.mipmap.ic_hide_password_foreground),
                        contentDescription = "Hide Password")
                }
            } else {
                IconButton(onClick = { showPassword = true }, modifier = Modifier
                    .sizeIn(25.dp,25.dp,55.dp,55.dp)) {
                    Icon(painter = painterResource(R.mipmap.ic_show_password_foreground),
                        contentDescription = "Show Password")
                }
            }
        })
        Button(onClick = {
            context.startActivity(Intent(context, MainActivity::class.java))
            activity?.finish()
        }, modifier = Modifier
            .align(Alignment.CenterHorizontally)) {
            Text("Авторизоваться", fontSize = 18.sp)
        }
    }
}
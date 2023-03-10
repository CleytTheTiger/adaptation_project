package com.example.adaptationproject

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.*
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.adaptationproject.ui.theme.AdaptationProjectTheme
import org.json.JSONObject
import com.example.adaptationproject.Constants.BASE_URL

class loginActivity : ComponentActivity() {
    private val active_user_model : ActiveUser by viewModels()
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
                    val lifeShit = LocalLifecycleOwner.current
                    LoginScreen(context, lifeShit, active_user_model)
                }
            }
        }
    }

    @Composable
    fun LoginScreen(context: Context, lifecycleOwner: LifecycleOwner ,userModel : ActiveUser) {
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
                var dialog = ProgressDialog.show(context, "", "Авторизация", true)
                fetch_user(context, login.value, password.value, userModel)
                userModel._currentUser.observe(lifecycleOwner, Observer {
                    if (userModel._currentUser.value != null){
                        Toast.makeText(context, "Вход успешно выполнен!",Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, MainActivity::class.java)
                        intent.putExtra("id",userModel._currentUser.value.toString())
                        dialog?.dismiss()
                        context.startActivity(intent)
                        activity?.finish()
                    }
                    else {
                        Toast.makeText(context, "Ошибка входа", Toast.LENGTH_SHORT).show()
                    }
                })

            }, modifier = Modifier
                .align(Alignment.CenterHorizontally)) {
                Text("Авторизоваться", fontSize = 18.sp)
            }
        }
    }

    fun fetch_user(context: Context, login: String, password: String, userModel: ActiveUser){
        val url = BASE_URL + "api/Authorization.php"
        val requestQueue = Volley.newRequestQueue(context)
        val stringRequest = object : StringRequest(Request.Method.POST, url,
            {response ->
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.get("response").equals("success")) {
                        val jsonArray = jsonObject.getJSONArray("data")
                        Log.d("Success: ", jsonObject.get("response").toString())
                        for (i in 0..jsonArray.length() - 1) {
                            val jo = jsonArray.getJSONObject(i)
                            userModel.setCurrentUser(jo.get("user_id").toString())
                        }
                    }}
                catch (e: java.lang.Exception){

                }
            }, { error ->
                Log.d("Error: ", error.toString()) }
        ) {
            override fun getParams(): HashMap<String,String>{
                val params = HashMap<String,String>()
                params["login"] = login
                params["password"] = password
                return params
            }
        }
        requestQueue.add(stringRequest)
        requestQueue.start()
    }
}


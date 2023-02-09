@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.example.adaptationproject

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.adaptationproject.Constants.BASE_URL
import com.example.adaptationproject.ui.theme.*
import org.json.JSONObject

@Composable
    fun HomeScreen(context: Context, user_id: String) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Column {
                Information(context, user_id)
                Menu(
                    listOf(
                        menuItem(
                            title = "Информация",
                            R.mipmap.ic_info_foreground,
                            Purple200,
                        ),
                        menuItem(
                            title = "Тесты и курсы",
                            R.mipmap.ic_test_foreground,
                            CustomRed1,
                        ),
                        menuItem(
                            title = "Мероприятия",
                            R.mipmap.ic_events_foreground,
                            CustomBlue1,
                        ),
                        menuItem(
                            title = "Карта",
                            R.mipmap.ic_map_foreground,
                            CustomGreen1,
                        )
                    ), context, user_id
                )
            }
        }
    }

    fun getUserData(context: Context, uname:MutableState<String>, udept:MutableState<String>, urole:MutableState<String>, uid : String){
        val url = BASE_URL + "GetUserData.php"
        val requestQueue = Volley.newRequestQueue(context)
        val stringRequest = object : StringRequest(
            Method.POST, url,
            {response ->
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.get("response").equals("success")) {
                        val jsonArray = jsonObject.getJSONArray("data")
                        Log.d("Success: ", jsonObject.get("response").toString())
                        for (i in 0 until jsonArray.length()) {
                            val jo = jsonArray.getJSONObject(i)
                            val name = jo.getString("name")
                            val secName = jo.getString("second_name")
                            val surname = jo.getString("surname")
                            uname.value = "$secName $name $surname"
                            udept.value = jo.getString("dept_name")
                            urole.value = jo.getString("role_name")
                        }
                    }}
                catch (e: java.lang.Exception){
                    Toast.makeText(context,"Ошибка получения данных!", Toast.LENGTH_SHORT).show()
                }
            }, { error ->
                Log.d("Error: ", error.toString()) }
        ) {
            override fun getParams(): HashMap<String,String>{
                val params = HashMap<String,String>()
                params["id"] = uid.toString()
                return params
            }
        }
        requestQueue.add(stringRequest)
        requestQueue.start()
    }

    @Composable
    fun Information(context: Context, id: String) {
        val name = remember {
            mutableStateOf("")
        }
        val dept = remember {
            mutableStateOf("")
        }
        val role = remember {
            mutableStateOf("")
        }
        getUserData(context, name, dept, role, id)
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.DarkGray)
                        .size(120.dp)
                )

            }
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.DarkGray)
                ) {
                    Column {
                        Text(
                            text = name.value, color = TextWhite,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                        Text(
                            text = "Институт " + dept.value, color = TextWhite,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                        Text(
                            text = role.value, color = TextWhite,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Menu(menuContent: List<menuItem>, context: Context, user_id : String) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceAround) {
            Text(
                text = "Доступные действия",
                modifier = Modifier
                    .padding(15.dp)
            )
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 4.dp, end = 4.dp, bottom = 100.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(menuContent.size) {
                    MenuItem(item = menuContent[it], context, user_id)
                }
            }
        }
    }

    @Composable
    fun MenuItem(item: menuItem, context: Context, user_id : String) {
        Box(modifier = Modifier
            .height(160.dp)
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(item.viewColor)
            .clickable {
                when (item.title) {
                    "Карта" -> {
                        val intent = Intent(context, MapsActivity::class.java)
                        //intent.putExtra("sql_query", query)
                        intent.putExtra("id", user_id)
                        context.startActivity(intent)
                    }
                    "Информация" -> {
                        context.startActivity(Intent(context, InfoActivity::class.java))
                    }
                    "Мероприятия" -> {
                        context.startActivity(Intent(context, EventActivity::class.java))
                    }
                    "Тесты и курсы" -> {
                        context.startActivity(Intent(context, CoursesActivity::class.java))
                    }
                }
            }
        ) {
            Text(
                text = item.title,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(5.dp)
            )
            Icon(
                painter = painterResource(id = item.iconId),
                contentDescription = item.title,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(95.dp, 122.dp)
            )
        }
    }

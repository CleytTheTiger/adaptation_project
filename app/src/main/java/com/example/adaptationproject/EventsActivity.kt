package com.example.adaptationproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.adaptationproject.ui.theme.AdaptationProjectTheme
import org.json.JSONObject

class EventsActivity : ComponentActivity() {
    var user_id = ""
    var inst_id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user_id = intent.getStringExtra("id").toString()
        inst_id = intent.getStringExtra("inst_id").toString()
        setContent {

            AdaptationProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val context = LocalContext.current
                    EventsScreenView(context)
                }
            }
        }
    }

    @Composable
    fun NavigationGraph(navController: NavHostController, context : Context) {
        NavHost(navController, startDestination = BottomNavItem.AvailableEvents.screen_route) {
            composable(BottomNavItem.AvailableEvents.screen_route) {
                val availableEvents = remember { mutableStateListOf<eventMenuItem>() }
                getAvailableEvents(availableEvents, context)
                AvailableEventScreen(context, availableEvents, user_id)
            }
            composable(BottomNavItem.SignedEvents.screen_route) {
                val signedEvents = remember { mutableStateListOf<eventMenuItem>() }
                getSignedEvents(signedEvents, context)
                SignedEventScreen(context, signedEvents, user_id)
            }
            //composable(BottomNavItem.SignedEvents.screen_route) {
            //    val eventDetails = remember { mutableStateListOf<eventMenuItem>() }
            //    EventDetailsScreen(eventDetails)
            //}
        }
    }

    @Composable
    fun BottomNavigation(navController: NavController) {
        val items = listOf(
            BottomNavItem.AvailableEvents,
            BottomNavItem.SignedEvents,
        )
        BottomNavigation(
            backgroundColor = colorResource(id = com.google.maps.android.compose.R.color.material_grey_50),
            contentColor = Color.Black
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    icon = { "" },
                    label = { Text(modifier = Modifier.align(Alignment.CenterVertically),text = item.title, fontSize = 16.sp) },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Black.copy(0.4f),
                    alwaysShowLabel = true,
                    selected = currentRoute == item.screen_route,
                    onClick = {
                        navController.navigate(item.screen_route) {

                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun EventsScreenView(context : Context){
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavigation(navController = navController) }
        ) {
            NavigationGraph(navController = navController, context)
        }
    }

    fun getAvailableEvents(availableEvents : MutableList<eventMenuItem>, context : Context){
        val url = Constants.BASE_URL + "api/GetAvailableEvents.php"
        val requestQueue = Volley.newRequestQueue(context)
        val stringRequest = object : StringRequest(
            Method.POST, url,
            {response ->
                availableEvents.clear()
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.get("response").equals("success")) {
                        val jsonArray = jsonObject.getJSONArray("data")
                        Log.d("Success: ", jsonObject.get("response").toString())
                        Log.d("----------------------------", "-")
                        for (i in 0..jsonArray.length() - 1) {
                            val jo = jsonArray.getJSONObject(i)
                            val id = jo.getString("id")
                            val name = jo.getString("event_name")
                            val desc = jo.getString("event_desc")
                            val date = jo.getString("event_date")
                            val address = jo.getString("event_address")
                            val icon = ""
                            val event = eventMenuItem(id, name, date, icon, desc, address)
                            availableEvents.add(event)
                            Log.d("Name: ", name)

                        }
                        Log.d("----------------------------", "-")
                    }}
                catch (e: java.lang.Exception){
                    Log.d("Error", e.toString())
                }
            }, { error ->
                Log.d("Error: ", error.toString()) }
        ) {
            override fun getParams(): HashMap<String,String>{
                val params = HashMap<String,String>()
                params["user_id"] = user_id
                params["inst_id"] = inst_id
                return params
            }
        }
        requestQueue.add(stringRequest)
        requestQueue.start()
    }
    fun getSignedEvents(signedEvents : MutableList<eventMenuItem>, context : Context){
        val url = Constants.BASE_URL + "api/GetSignedEvents.php"
        val requestQueue = Volley.newRequestQueue(context)
        val stringRequest = object : StringRequest(
            Method.POST, url,
            {response ->
                signedEvents.clear()
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.get("response").equals("success")) {
                        val jsonArray = jsonObject.getJSONArray("data")
                        Log.d("Success: ", jsonObject.get("response").toString())
                        Log.d("----------------------------", "-")
                        for (i in 0..jsonArray.length() - 1) {
                            val jo = jsonArray.getJSONObject(i)
                            val id = jo.getString("id")
                            val name = jo.getString("event_name")
                            val desc = jo.getString("event_desc")
                            val date = jo.getString("event_date")
                            val address = jo.getString("event_address")
                            val icon = ""
                            val event = eventMenuItem(id, name, date, icon, desc, address)
                            signedEvents.add(event)
                            Log.d("Name: ", name)

                        }
                        Log.d("----------------------------", "-")
                    }}
                catch (e: java.lang.Exception){
                    Log.d("Error", e.toString())
                }
            }, { error ->
                Log.d("Error: ", error.toString()) }
        ) {
            override fun getParams(): HashMap<String,String>{
                val params = HashMap<String,String>()
                params["user_id"] = user_id
                return params
            }
        }
        requestQueue.add(stringRequest)
        requestQueue.start()
    }
}


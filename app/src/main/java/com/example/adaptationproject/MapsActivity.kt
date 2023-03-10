package com.example.adaptationproject

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.adaptationproject.Constants.BASE_URL
import com.google.maps.android.compose.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import org.json.JSONObject

class MapsActivity : ComponentActivity() {
    //private val query = intent.getStringExtra("sql_query")
    var user_id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user_id = intent.getStringExtra("id").toString()
        setContent {
            val context = LocalContext.current
            val points = remember { mutableStateListOf<MapLocation>() }
            getPointsData(context, points)
            Map(context, points)
        }
    }

    @Composable
    private fun Map(context : Context, points : MutableList<MapLocation>){
        if (points.size > 0) {
            Log.d("Status: ","POIs found!")
            val cameraPosition = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(LatLng(points[0].lat, points[0].long), 18f)
            }
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPosition
            ) {
                for (i in 0..points.size-1) {
                    Marker(
                        position = LatLng(points[i].lat, points[i].long),
                        title = points[i].name,
                        snippet = points[i].desc
                    )
                }
            }
        }
        else {
            Log.d("Status: ","No POIs found!")
            //Toast.makeText(context, "No POIs found!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getPointsData(context : Context, points : MutableList<MapLocation>){
        val url = BASE_URL + "api/GetPOIs.php"
        val requestQueue = Volley.newRequestQueue(context)
        val stringRequest = object : StringRequest(
            Method.POST, url,
            {response ->
                points.clear()
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.get("response").equals("success")) {
                        val jsonArray = jsonObject.getJSONArray("data")
                        Log.d("Success: ", jsonObject.get("response").toString())
                        Log.d("----------------------------", "-")
                        for (i in 0..jsonArray.length() - 1) {
                            val jo = jsonArray.getJSONObject(i)
                            val name = jo.getString("POI_Name")
                            val desc = jo.getString("POI_desc")
                            val lat = jo.getString("POI_lat").toDouble()
                            val long = jo.getString("POI_long").toDouble()
                            val point = MapLocation(name, desc, lat, long)
                            Log.d("Name: ", name)
                            points.add(point)
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
package com.example.adaptationproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.maps.android.compose.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState

class MapsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Map()
        }
    }

    @Composable
    private fun Map(){
        val markerNames = mutableListOf("Корпус Г", "Корпус А", "Корпус В; Библиотека")
        val markerSnippets = mutableListOf("ЯГТУ", "ЯГТУ", "ЯГТУ")
        val markerPos = mutableListOf(
            LatLng(57.58655030808919, 39.855244997172996),
            LatLng(57.588104, 39.856180),
            LatLng(57.58453159302147, 39.86252589148445)
        )
        val cameraPosition = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(markerPos[0],18f)
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPosition
        ){
            for(i in markerNames.indices){
                Marker(
                    position = markerPos[i],
                    title = markerNames[i],
                    snippet = markerSnippets[i]
                )
            }
        }
    }
}
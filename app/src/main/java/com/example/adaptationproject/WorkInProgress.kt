package com.example.adaptationproject

import android.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun WorkInProgress(){
    Column(modifier = Modifier
        .fillMaxSize())
    {
        Icon(painter = painterResource(id = R.mipmap.ic_work_in_progress_foreground),
            contentDescription = "Раздел недоступен!",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(15.dp,50.dp,15.dp,15.dp))
        Text(text = "Работы над этим разделом ещё не окончены...",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp)
                .widthIn(150.dp,200.dp))
    }
}
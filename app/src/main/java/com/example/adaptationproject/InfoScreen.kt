package com.example.adaptationproject

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.adaptationproject.ui.theme.AdaptationProjectTheme

@Composable
fun InfoScreen() {
    InfoMenu(listOf(
        infoMenuItem("Малый блок информации №1", "Здесь будет находиться небольшая информация"),
        infoMenuItem("Малый блок информации №2", "Здесь будет находиться небольшая информация"),
        infoMenuItem("Большой блок информации №1", "Здесь будет находиться большой объём информации. Например, данные об учебном заведении или организации"),
        infoMenuItem("Малый блок информации №3", "Здесь будет находиться небольшая информация"),
    ))
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InfoMenu(menuContent: List<infoMenuItem>) {
    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween) {
        Text(text = "Доступная информация",
            modifier = Modifier
                .padding(15.dp)
                .align(alignment = Alignment.CenterHorizontally))
        LazyVerticalGrid(cells = GridCells.Fixed(1),
            contentPadding = PaddingValues(start = 4.dp, end = 4.dp, bottom = 100.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(menuContent.size) {
                InfoMenuItem(item = menuContent[it])
            }
        }
    }
}

@Composable
fun InfoMenuItem(item: infoMenuItem) {
    Box(modifier = Modifier
        .widthIn(50.dp, 450.dp)
        .padding(8.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(Color.DarkGray)
    ) {
        Column {
            Text(text = item.title,
                modifier = Modifier
                    .padding(5.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
            Text(text = item.text,
                modifier = Modifier
                    .padding(5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InterfacePreview(){
    AdaptationProjectTheme {
        InfoScreen()
    }
}
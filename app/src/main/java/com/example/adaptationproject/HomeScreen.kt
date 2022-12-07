package com.example.adaptationproject

import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.adaptationproject.ui.theme.*

@Composable
fun HomeScreen(context: Context){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Information(
                "Иванов Иван Иванович",
                "Информационные системы и технологии",
                "3 курс"
            )
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
                ), context
            )
        }
    }
}

@Composable
fun Information(name: String, role: String, work_time: String) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)) {
        Column(
            verticalArrangement = Arrangement.Center
        ){
            Icon(painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "User Avatar",
                modifier = Modifier
                    .padding(15.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.DarkGray)
                    .size(120.dp))

        }
        Column(
            verticalArrangement = Arrangement.Center
        ){
            Box(
                modifier = Modifier
                    .padding(15.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.DarkGray)
            ) {
                Column {
                    Text(
                        text = name, color = TextWhite,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                    Text(
                        text = role, color = TextWhite,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                    Text(
                        text = work_time, color = TextWhite,
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
fun Menu(menuContent: List<menuItem>, context: Context){
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceAround) {
        Text(text = "Доступные действия",
            modifier = Modifier
            .padding(15.dp))
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 100.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(menuContent.size) {
                MenuItem(item = menuContent[it], context)
            }
        }
    }
}

@Composable
fun MenuItem(item: menuItem, context: Context){
    Box(modifier = Modifier
        .height(150.dp)
        .fillMaxWidth()
        .padding(15.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(item.viewColor)
        .clickable {
            when(item.title){
                "Карта" -> {
                    context.startActivity(Intent(context, MapsActivity::class.java))
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
                .size(55.dp)
        )
    }
}
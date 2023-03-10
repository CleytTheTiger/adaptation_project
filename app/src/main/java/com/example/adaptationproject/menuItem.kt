package com.example.adaptationproject

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class menuItem(
    val title: String,
    @DrawableRes val iconId: Int,
    val viewColor: Color,
)

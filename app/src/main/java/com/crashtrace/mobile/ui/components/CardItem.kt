package com.crashtrace.mobile.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class CardItem(
    val cardId: String,
    val title: String,
    val description: String,
    val imagePlaceholderColor: Color,
    val accentColor: Color,
    val imagePainter: Painter? = null
)
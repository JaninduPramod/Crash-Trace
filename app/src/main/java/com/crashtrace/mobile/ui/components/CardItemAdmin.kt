package com.crashtrace.mobile.ui.components // Or your appropriate models package

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class CardItemAdmin(
    val cardId: String,
    val title: String,
    val description: String,
    val imagePainter: Painter? = null,
    val imagePlaceholderColor: Color,
    val accentColor: Color,
    val status: String
)
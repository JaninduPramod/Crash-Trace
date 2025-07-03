package com.crashtrace.mobile.ui.components

import androidx.compose.ui.graphics.Color

data class CardItem(
    val cardId: String,
    val title: String,
    val description: String,
    val imagePlaceholderColor: Color,
    val accentColor: Color,
    var isExpanded: Boolean = false
)
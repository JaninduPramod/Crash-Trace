package com.crashtrace.mobile.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NavBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    Surface(
        tonalElevation = 8.dp,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp), // Added top padding
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // HOME
            NavBarItem(
                iconRes = com.crashtrace.mobile.R.drawable.home_icon,
                label = "HOME",
                selected = selectedIndex == 0,
                onClick = { onItemSelected(0) }
            )
            // NEWS
            NavBarItem(
                iconRes = com.crashtrace.mobile.R.drawable.news_icon,
                label = "NEWS",
                selected = selectedIndex == 1,
                onClick = { onItemSelected(1) }
            )
            // REPORT
            NavBarItem(
                iconRes = com.crashtrace.mobile.R.drawable.search_report_icon,
                label = "SEARCH",
                selected = selectedIndex == 2,
                onClick = { onItemSelected(2) }
            )
            // WRITE REPORT
            NavBarItem(
                iconRes = com.crashtrace.mobile.R.drawable.report_icon,
                label = "WRITE",
                selected = selectedIndex == 3,
                onClick = { onItemSelected(3) }
            )
        }
    }
}

@Composable
fun NavBarItem(
    iconRes: Int,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val contentColor = if (selected) Color(0xFFFF2D2D) else Color(0xFF888888)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 6.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            tint = contentColor,
            modifier = Modifier.height(28.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            color = contentColor,
            fontSize = 10.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NavBarPreview() {
    NavBar(selectedIndex = 0, onItemSelected = {})
}

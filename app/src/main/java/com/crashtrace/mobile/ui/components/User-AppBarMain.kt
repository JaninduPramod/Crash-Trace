package com.crashtrace.mobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.crashtrace.mobile.R


@Composable
fun AppBarMain(
    title: String,
    showOverlay: Boolean = false,
    backButton: Boolean = true,
    onBackClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    BackButton: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFF2D2D))
        )

        if (showOverlay) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xCC000000))
                    .zIndex(999f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 45.dp, start = 12.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            if (BackButton) {
                Image(
                    painter = painterResource(id = R.drawable.back_button_icon),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onBackClick() }
                )
            } else {
                Spacer(modifier = Modifier.width(32.dp))
            }

            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

            Image(
                painter = painterResource(id = R.drawable.user2_icon_profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onProfileClick() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppBarMainPreview() {
    AppBarMain(title = "FIND LOCATION", BackButton = false)
}

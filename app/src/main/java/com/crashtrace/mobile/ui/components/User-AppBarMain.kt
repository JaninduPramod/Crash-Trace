package com.crashtrace.mobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.crashtrace.mobile.R
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun AppBarMain(
    title: String,
    showOverlay: Boolean = false,
    backButton: Boolean = true,
    onBackClick: () -> Unit = {},
    navController: NavHostController? = null,
    BackButton: Boolean,
    onProfileClick: (Boolean) -> Unit = {} // <-- Always include this parameter
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
        ){
            // Transparent background image over red
            Image(
                painter = painterResource(id = R.drawable.app_backgroung),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(1f), // Adjust transparency here
                contentScale = ContentScale.Crop
            )
            Image(
                painter = painterResource(id = R.drawable.app_backgroung),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(1f), // Adjust transparency here
                contentScale = ContentScale.Crop
            )
        }





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

            Button(
                onClick = {
                    onProfileClick(true) // <-- Always call this for profile button
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp),
                elevation = null,
                modifier = Modifier.size(32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user2_icon_profile),
                    contentDescription = "Profile",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppBarMainPreview() {
    AppBarMain(
        title = "FIND LOCATION",
        navController = rememberNavController(),
        BackButton = true,
        onProfileClick = {}
    )
}

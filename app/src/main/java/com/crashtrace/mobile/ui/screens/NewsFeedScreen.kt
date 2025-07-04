package com.crashtrace.mobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crashtrace.mobile.R
import com.crashtrace.mobile.ui.components.AppBarMain
import com.crashtrace.mobile.ui.components.MyCustomCard
import com.crashtrace.mobile.ui.components.CardItem
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun NewsFeedScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        // White top inner shadow (70dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.5f),
                            Color.White.copy(alpha = 0f)
                        )
                    )
                )
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Static AppBar
            AppBarMain(title = "HOT NEWS", BackButton = false)

            // Scrollable content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(0.dp),
                            clip = false
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.accident),
                        contentDescription = "Main News",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding(horizontal = 0.dp)
                            .clip(androidx.compose.foundation.shape.RoundedCornerShape(0.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp)
                            .offset(y = 200.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                                clip = false
                            ),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "HOT NEWS",
                                color = Color(0xFFFF2D2D),
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                fontStyle = FontStyle.Italic
                            )
                            Text(
                                text = "2025/04/28",
                                color = Color(0xFFFF2D2D),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = "Preview Card Title",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                            Text(
                                text = "Another common cause of auto damage: having a parked vehicle hit by another car. Whether you're leaving your car in a parking lot or on the road, take steps to help avoid parked car collisions and claims. Here are some suggestions:",
                                color = Color.Black.copy(alpha = 0.5f),
                                fontSize = 12.sp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                            Button(
                                onClick = { navController.navigate("card") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                                    .padding(top = 8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2D2D)),
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "SHOW MORE",
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Image(
                                        painter = painterResource(id = R.drawable.arrow_right),
                                        contentDescription = "Arrow",
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(210.dp))

                // HOT NEWS section title
                Text(
                    text = "HOT NEWS",
                    color = Color(0xFF7A7A7A),
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                )

                // News list
                val newsList = listOf(
                    CardItem(
                        cardId = "1",
                        title = "Preview Card Title",
                        description = "this is a sample description for the preview. it shows how text will appear and",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFFFFA500),
                        imagePainter = painterResource(id = R.drawable.accident)
                    ),
                    CardItem(
                        cardId = "2",
                        title = "Preview Card Title",
                        description = "this is a sample description for the preview. it shows how text will appear and",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFFFF2D2D)
                    ),
                    CardItem(
                        cardId = "3",
                        title = "Preview Card Title",
                        description = "this is a sample description for the preview. it shows how text will appear and",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFF2196F3)
                    ),
                    CardItem(
                        cardId = "4",
                        title = "Preview Card Title",
                        description = "this is a sample description for the preview.",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFF000000)
                    ),
                    CardItem(
                        cardId = "5",
                        title = "Preview Card Title",
                        description = "this is a sample description for the preview. it shows how text will appear and might wrap",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFF2196F3)
                    )
                )
                Column(
                    modifier = Modifier.padding(0.dp, 8.dp, 8.dp, 8.dp)
                ) {
                    newsList.forEach { item ->
                        MyCustomCard(cardItem = item)
                    }
                }

                // Bottom Show More button
                Button(
                    onClick = { navController.navigate("newsGallery")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2D2D)),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "SHOW MORE",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.arrow_right),
                            contentDescription = "Arrow",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsFeedScreenPreview() {
//    NewsFeedScreen()
}

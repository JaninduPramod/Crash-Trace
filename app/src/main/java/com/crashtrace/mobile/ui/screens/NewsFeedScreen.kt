package com.crashtrace.mobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crashtrace.mobile.R
import com.crashtrace.mobile.ui.components.AppBarMain
import com.crashtrace.mobile.ui.components.MyCustomCard
import com.crashtrace.mobile.viewmodel.NewsGalleryViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsFeedScreen(
    navController: NavController
) {
    val newsGalleryViewModel: NewsGalleryViewModel = koinViewModel()
    val newsList by newsGalleryViewModel.newsList.collectAsState()
    val lastItem = newsList.lastOrNull()
    var loadProfile by remember { mutableStateOf(false) }


    if (loadProfile) {
        navController.navigate("profile")
        loadProfile = false
    }

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
            AppBarMain(
                title = "HOT NEWS",
                BackButton = false,
                onProfileClick = { isProfile -> if (isProfile) loadProfile = true }
            )

            // Scrollable content
            Column(
                modifier = Modifier
                    .weight(1f)
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(0.dp))
                    ) {

                            AsyncImage(
                                model = lastItem?.imageUrl,
                                contentDescription = "Main News",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.matchParentSize(),
                                placeholder = rememberAsyncImagePainter(model = R.drawable.loading),
                                error = rememberAsyncImagePainter(model = R.drawable.loading)
                            )



                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
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
                            if (lastItem != null) {
                                Text(
                                    text = "HOT NEWS",
                                    color = Color(0xFFFF2D2D),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    fontStyle = FontStyle.Italic
                                )
                                Text(
                                    text = lastItem.date, // or formatDate(lastItem.date) if needed
                                    color = Color(0xFFFF2D2D),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Text(
                                    text = lastItem.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = lastItem.description,
                                    color = Color.Black.copy(alpha = 0.5f),
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                            } else {
                                Text(
                                    text = "No hot news available.",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                            }
                            Button(
                                onClick = {
                                    lastItem?.let {
                                        navController.navigate("card/${it.cardId}")
                                    }
                                },
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
                                        text = "View NEWS",
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

                Column(
                    modifier = Modifier.padding(start = 0.dp, end = 8.dp, bottom = 8.dp)
                ) {
                    newsList.takeLast(5).forEach { item ->
                        MyCustomCard(cardItem = item)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate("gallery") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(horizontal = 16.dp),
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
    NewsFeedScreen(navController = rememberNavController())
}


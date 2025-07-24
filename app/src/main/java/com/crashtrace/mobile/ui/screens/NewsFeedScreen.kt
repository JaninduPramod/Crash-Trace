package com.crashtrace.mobile.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.crashtrace.mobile.network.SupabaseClient
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsFeedScreen(
    navController: NavHostController
) {
    val newsGalleryViewModel: NewsGalleryViewModel = koinViewModel()
    val newsList by newsGalleryViewModel.newsList.collectAsState()
    val lastItem = newsList.lastOrNull() // Still assuming this means the latest item in an oldest-first list or just the main item
    val selectedItem = newsList.find { it.cardId == lastItem?.cardId }
    val imageUrl2 = SupabaseClient.getImageUrl("${selectedItem?.vehiclenub}.jpg")
    var loadProfile by remember { mutableStateOf(false) }

    fun handleOnShowMore() {
        newsGalleryViewModel.getNewsList()
        navController.navigate("gallery")
    }

    LaunchedEffect(Unit) {
        newsGalleryViewModel.getNewsList()
    }

    if (loadProfile) {
        navController.navigate("profile")
        loadProfile = false
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
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
            AppBarMain(
                title = "HOT NEWS",
                BackButton = false,
                onProfileClick = { isProfile -> if (isProfile) loadProfile = true }
            )

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
                            shape = RoundedCornerShape(0.dp),
                            clip = false
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(0.dp))
                    ) {


                        if (lastItem?.vehiclenub != null) {
                            AsyncImage(
                                model = SupabaseClient.getImageUrl("${lastItem?.vehiclenub}.jpg"),
                                contentDescription = "Main News",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(Color(0xFFF0F0F0)),
                            )
                        } else {
                            GoogleBarsPulsePlaceholder(modifier = Modifier.matchParentSize())
                        }
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                            .offset(y = 200.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(16.dp),
                                clip = false
                            ),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
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
                                    text = lastItem.date,
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
                                        navController.navigate("cardU/${it.cardId}")
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                                    .padding(top = 8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2D2D)),
                                shape = RoundedCornerShape(12.dp)
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
                    // --- THE KEY CHANGE FOR REVERSE ORDER ---
                    // Take the first 5 (newest items) and then reverse that sublist
                    newsList.take(5).reversed().forEach { item ->
                        MyCustomCard(
                            cardItem = item,
                            onClick = {
                                navController.navigate("cardU/${item.cardId}")
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { handleOnShowMore() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF2D2D)),
                    shape = RoundedCornerShape(12.dp)
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



@Composable
fun GoogleBarsPulsePlaceholder(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()

    val delays = listOf(0, 100, 200, 300)
    val alphas = delays.map { delay ->
        infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 600,
                    delayMillis = delay,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    val colors = listOf(Color.Blue, Color.Red, Color(0xFFFFC107), Color.Green)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            alphas.forEachIndexed { index, alpha ->
                Box(
                    modifier = Modifier
                        .size(width = 8.dp, height = 24.dp)
                        .graphicsLayer { this.alpha = alpha.value }
                        .background(colors[index], shape = RoundedCornerShape(4.dp))
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun NewsFeedScreenPreview() {
    NewsFeedScreen(navController = rememberNavController())
}


package com.crashtrace.mobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.crashtrace.mobile.R
import com.crashtrace.mobile.ui.components.AppBarMain

import com.crashtrace.mobile.ui.components.MyCustomCard
import com.crashtrace.mobile.ui.components.CardItem
import com.crashtrace.mobile.viewmodel.NewsGalleryViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsGalleryScreen(
    navController: NavController,
    selectedIndex: Int = 1
) {

    val newsGalleryViewModel: NewsGalleryViewModel = koinViewModel()


    LaunchedEffect(Unit) {
        newsGalleryViewModel.getNewsList()
    }

    val newsList by newsGalleryViewModel.newsList.collectAsState()

    // Fetch all news when the screen first loads
    LaunchedEffect(Unit) {
        newsGalleryViewModel.getNewsList()
    }

    var loadProfile by remember { mutableStateOf(false) }
    var backToFeed by remember { mutableStateOf(false) }

    if (loadProfile) {
        navController.navigate("profile")
        loadProfile = false
    }



    if (backToFeed) {
        // Assuming MainNavScreen is the main navigation entry point
        MainNavScreen(navController = navController, selectedIndex = selectedIndex)
        return
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Top gradient overlay
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 1f),
                            Color.Transparent
                        )
                    )
                )
        )

        Column(modifier = Modifier.fillMaxSize()) {
            AppBarMain(
                title = "NEWS GALLERY",
                BackButton = true,
                onBackClick = { navController.navigate("mainScreen/1") },
                onProfileClick = { navController.navigate("profile") },
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "NEWS Gallery",
                color = Color(0xFF343434),
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.padding(start = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(0.dp, 8.dp, 8.dp, 8.dp)
            ) {

                newsList.reversed().forEach { item ->
                    MyCustomCard(
                        cardItem = item,
                        onClick = {
                            navController.navigate("cardU/${item.cardId}?origin=from_gallery")
                        }
                    )
                }
                LaunchedEffect(newsList) {
                    println(">>> News list size: ${newsList.size}")
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsGalleryPreview() {
    NewsGalleryScreen(navController = rememberNavController())
}

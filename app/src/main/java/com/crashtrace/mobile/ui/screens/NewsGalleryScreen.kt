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
import androidx.compose.material3.CardDefaults
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

@Composable
fun NewsGalleryScreen() {
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

        // Gradient shadow box at top
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

        // Main content
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Static AppBar
            AppBarMain(title = "NEWS GALLERY", BackButton = true)

            // Scrollable content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                // Section Title
                Text(
                    text = "NEWS Gallery",
                    color = Color(0xFF343434),
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(start = 16.dp, bottom = 0.dp)
                )

                // News items
                val newsList = listOf(
                    CardItem(
                        cardId = "1",
                        title = "Preview Card Title",
                        description = "this is a sample description for the preview. it shows how text will appear and",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFFFFA500),
                        imagePainter = painterResource(id = R.drawable.accident)
                    ),

                    CardItem("2", "Card Title 2", "Latest head lines show that sed do eius mod tempor  headlines show that sed do eiusmod tempor headlines show that sed do eiusmod tempor incididunt ut labore.", Color.Gray, Color(0xFFFF2D2D)),
                    CardItem("3", "Card Title 3", "Aliquam erat volu tpat. Ut enim ad minim veniam, quis nostrud exerc itation.", Color.Gray, Color(0xFF2196F3)),
                     CardItem("5", "Card Title 5", "Quick update: Excepteur sint occaecat cupidatat non proident, sunt in culpa.", Color.Gray, Color(0xFFFFA500)),
                    CardItem("6", "Card Title 6", "Flash report: Nemo enim ips am volupt atem quia voluptas sit aspernatur.", Color.Gray, Color(0xFFFF2D2D)),
                    CardItem("7", "Card Title 7", "In-depth analysis: Neque porro qu isquam est qui dolorem ipsum quia dolor sit amet.", Color.Gray, Color(0xFFFF2D2D)),
                    CardItem("8", "Card Title 8", "Special feature: Lorem ipsum dolor sit headlines  show that sed do eiusmod tempor headlines show that sed do eiusmod tempor amet, consectetur adipiscing elit.", Color.Gray, Color(0xFFFF9800)),
                    CardItem("4", "Card Title 4", "New developments revealed: Duis headlines show that sed do  eiusmod tempor aute irure dolor in reprehenderit.", Color.Gray, Color(0xFF52B3FF)),
                    CardItem("9", "Card Title 9", "Update just in: Sed ut  pers piciatis unde omnis iste natus error sit volu ptatem.", Color.Gray, Color(0xFF52B3FF)),
                    CardItem("10", "Card Title 10", "Insight: But I must explain to you how all this mistaken idea of denou ncing pleasure.", Color.Gray, Color(0xFF52B3FF)),
                    CardItem("11", "Card Title 11", "Field report: At vero eos et acc usamus et iusto odio headlines show that sed do eiusmod tempor dignissimos ducimus.", Color.Gray, Color(0xFFFF2D2D)),
                    CardItem("12", "Card Title 12", "News highlight: On the other hand, we denounce with righteous indignation.", Color.Gray, Color(0xFFFFA500)),
                    CardItem("13", "Card Title 13", "Detailed review: These cases are perfectly headlines show that sed do eiusmod tempor simple and easy to distinguish.", Color.Gray, Color(0xFFFF2D2D)),
                    CardItem("14", "Card Title 14", "Trending: Nor again is there anyone who loves or pursues or desires pain.", Color.Gray, Color(0xFF52B3FF)),
                    CardItem("15", "Card Title 15", "Editorial: Because it is pain, but because occas ionally headlines show that sed do eiusmod tempor circumstances occur.", Color.Gray, Color(0xFF52B3FF))
                    )


                Column(
                    modifier = Modifier.padding(0.dp, 8.dp, 8.dp, 8.dp)
                ) {
                    newsList.forEach { item ->
                        MyCustomCard(cardItem = item)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsGalleryPreview() {
    NewsGalleryScreen()
}

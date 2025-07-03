package com.crashtrace.mobile.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import com.crashtrace.mobile.ui.components.CardItemAdmin
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
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
import androidx.compose.ui.tooling.preview.Preview
import com.crashtrace.mobile.ui.components.CardActivityAdminCard


@Composable
fun AdminNewsGalleryScreen() {
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
            AppBarMain(title = "", BackButton = false)

            // Scrollable content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                // Section Title
                Text(
                    text = "ADMIN Gallery",
                    color = Color(0xFF343434),
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(start = 16.dp, bottom = 0.dp)
                )

                val newsList = listOf(
                    CardItemAdmin(
                        cardId = "2",
                        title = "Card Title 2",
                        description = "Latest head lines show that sed do eius mod tempor headlines show that sed do eiusmod tempor headlines show that sed do eiusmod tempor incididunt ut labore.",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFF808080),
                        status = "pending"
                    ),
                    CardItemAdmin(
                        cardId = "3",
                        title = "Card Title 3",
                        description = "Aliquam erat volutpat. Ut enim ad minim veniam, quis nostrud exercitation.",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFF808080),
                        status = "ok"
                    ),
                    CardItemAdmin(
                        cardId = "5",
                        title = "Card Title 5",
                        description = "Quick update: Excepteur sint occaecat cupidatat non proident, sunt in culpa.",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFF808080),
                        status = "bad"
                    ),
                    CardItemAdmin(
                        cardId = "6",
                        title = "Card Title 6",
                        description = "Flash report: Nemo enim ipsam voluptatem quia voluptas sit aspernatur.",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFF808080),
                        status = "ok"
                    ),
                    CardItemAdmin(
                        cardId = "7",
                        title = "Card Title 7",
                        description = "In-depth analysis: Neque porro quisquam est qui dolorem ipsum quia dolor sit amet.",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFF808080),
                        status = "pending"
                    ),
                    CardItemAdmin(
                        cardId = "8",
                        title = "Card Title 8",
                        description = "Special feature: Lorem ipsum dolor sit headlines show that sed do eiusmod tempor headlines show that sed do eiusmod tempor amet, consectetur adipiscing elit.",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFF808080),
                        status = "ok"
                    ),
                    CardItemAdmin(
                        cardId = "4",
                        title = "Card Title 4",
                        description = "New developments revealed: Duis headlines show that sed do eiusmod tempor aute irure dolor in reprehenderit.",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFF808080),
                        status = "bad"
                    ),
                    CardItemAdmin(
                        cardId = "9",
                        title = "Card Title 9",
                        description = "Update just in: Sed ut perspiciatis unde omnis iste natus error sit voluptatem.",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFF808080),
                        status = "ok"
                    ),
                    CardItemAdmin(
                        cardId = "10",
                        title = "Card Title 10",
                        description = "Insight: But I must explain to you how all this mistaken idea of denouncing pleasure.",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFF808080),
                        status = "pending"
                    ),
                    CardItemAdmin(
                        cardId = "11",
                        title = "Card Title 11",
                        description = "Field report: At vero eos et accusamus et iusto odio headlines show that sed do eiusmod tempor dignissimos ducimus.",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFF808080),
                        status = "bad"
                    ),
                    CardItemAdmin(
                        cardId = "12",
                        title = "Card Title 12",
                        description = "News highlight: On the other hand, we denounce with righteous indignation.",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFF808080),
                        status = "ok"
                    ),
                    CardItemAdmin(
                        cardId = "13",
                        title = "Card Title 13",
                        description = "Detailed review: These cases are perfectly headlines show that sed do eiusmod tempor simple and easy to distinguish.",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFF808080),
                        status = "pending"
                    ),
                    CardItemAdmin(
                        cardId = "14",
                        title = "Card Title 14",
                        description = "Trending: Nor again is there anyone who loves or pursues or desires pain.",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFF808080),
                        status = "ok"
                    ),
                    CardItemAdmin(
                        cardId = "15",
                        title = "Card Title 15",
                        description = "Editorial: Because it is pain, but because occasionally headlines show that sed do eiusmod tempor circumstances occur.",
                        imagePlaceholderColor = Color.Gray,
                        accentColor = Color(0xFF808080),
                        status = "bad"
                    )
                )




                Column(
                    modifier = Modifier.padding(0.dp, 8.dp, 8.dp, 8.dp) // padding(start, top, end, bottom)
                ) {
                    newsList.forEach { item ->
                        CardActivityAdminCard(cardItem = item)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminNewsGalleryScreenPreview() {
    AdminNewsGalleryScreen()
}

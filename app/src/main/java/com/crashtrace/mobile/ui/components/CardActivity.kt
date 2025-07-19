package com.crashtrace.mobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.crashtrace.mobile.R

@Composable
fun MyCustomCard(
    cardItem: CardItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current

    // Load image from URL
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(cardItem.imageUrl)
            .crossfade(true)
            .placeholder(R.drawable.loading) // Optional loading GIF
            .error(R.drawable.loading) // Optional error image
            .build()
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(start = 0.dp, top = 5.dp, end = 5.dp, bottom = 5.dp),
        shape = RoundedCornerShape(topStart = 0.dp, topEnd = 20.dp, bottomEnd = 20.dp, bottomStart = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .height(IntrinsicSize.Max)
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.4f)
                        .fillMaxHeight()
                        .padding(start = 0.dp, top = 5.dp, end = 15.dp, bottom = 5.dp)
                        .clip(RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp))
                        .background(Color.Gray)
                ) {
                    Image(
                        painter = painter,
                        contentDescription = cardItem.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(
                    modifier = Modifier
                        .width(4.dp)
                        .height(70.dp)
                        .align(Alignment.CenterVertically)
                        .background(cardItem.accentColor)
                )

                Column(
                    modifier = Modifier
                        .weight(0.6f)
                        .fillMaxHeight()
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 14.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = cardItem.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 4.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = cardItem.description,
                        fontSize = 10.sp,
                        color = Color.Black.copy(alpha = 0.5f),
                        style = LocalTextStyle.current.copy(lineHeight = 12.sp),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onClick() }
            )
        }
    }
}

data class CardItem(
    val cardId: String,
    val title: String,
    val description: String,
    val accentColor: Color,
    val imagePlaceholderColor: Color,
    val imageUrl: String,
    val date :String,
    val location :String,
    val locationUrl :String,
    val vehiclenub:String,
)

@Preview(showBackground = true)
@Composable
fun MyCustomCardPreview() {
    MyCustomCard(
        cardItem = CardItem(
            cardId = "1",
            title = "Sample Title",
            description = "This is a sample description that could be a bit long to trigger ellipsis.",
            imageUrl = "https://example.com/image1.jpg",
            accentColor = Color.Green,
            imagePlaceholderColor = Color.DarkGray,
            date = "2023-10-01",
            location = "Colombo, Sri Lanka",
            locationUrl = "https://www.google.com/maps/place/Colombo,+Sri+Lanka/@6.9271,79.8612,12z/data=!3m1!4b1!4m6!3m5!1s0x3ae259a7f8c8c9b5:0x7d8e2f8c8c8c8c8c!8m2!3d6.9271!4d79.8612!16zL20vMDNnYjQ",
            vehiclenub = "azy-1234",
        ),
        onClick = {}
    )
}

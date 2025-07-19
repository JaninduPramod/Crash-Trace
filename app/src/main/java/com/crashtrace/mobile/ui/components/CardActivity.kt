package com.crashtrace.mobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.imageLoader
import coil.request.ImageRequest
import com.crashtrace.mobile.R

@Composable
fun MyCustomCard(
    cardItem: CardItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
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
                val context = LocalContext.current

                Box(
                    modifier = Modifier
                        .weight(0.4f)
                        .fillMaxHeight()
                        .padding(start = 0.dp, top = 5.dp, end = 15.dp, bottom = 5.dp)
                        .clip(RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp))
                        .background(if (cardItem.imagePainter == null) cardItem.imagePlaceholderColor else Color.DarkGray)
                ) {
                    if (cardItem.imagePainter != null) {
                        Image(
                            painter = cardItem.imagePainter,
                            contentDescription = cardItem.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        val gifPainter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(context)
                                .data(R.drawable.loding) // loading gif in drawable
                                .build(),
                            imageLoader = context.imageLoader.newBuilder()
                                .components {
                                    add(GifDecoder.Factory())
                                }
                                .build()
                        )

                        Image(
                            painter = gifPainter,
                            contentDescription = cardItem.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
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

            // Full-size transparent clickable overlay instead of Button (better)
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
    val imagePainter: Painter? = null
)

@Preview(showBackground = true)
@Composable
fun MyCustomCardPreview() {
    MyCustomCard(
        cardItem = CardItem(
            cardId = "1",
            title = "Sample Title",
            description = "This is a sample description for the card. It can be a bit longer to show ellipsis.",
            imagePlaceholderColor = Color.Gray,
            accentColor = Color.Red,
        ),
        onClick = {} // empty lambda for preview
    )
}

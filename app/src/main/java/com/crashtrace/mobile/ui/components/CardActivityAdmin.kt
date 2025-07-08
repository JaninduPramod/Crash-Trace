package com.crashtrace.mobile.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.crashtrace.mobile.R
import kotlin.String
import com.crashtrace.mobile.ui.components.CardItemAdmin

@Composable
fun CardActivityAdminCard(cardItem: CardItemAdmin, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(start = 0.dp, top = 5.dp, end = 5.dp, bottom = 5.dp),
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 20.dp,
                bottomEnd = 20.dp,
                bottomStart = 0.dp
            ),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                // Vertical Column for the two boxes
                Column(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    // First Box (NEWS ID)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .padding(top = 8.dp, bottom = 4.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color(0xFFEAEAEA)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "NEWS ID:",
                                fontSize = 20.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Medium,
                            )

                        }
                    }

                    // Second Box (Image)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(top = 2.dp, bottom = 8.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color(0xFFEAEAEA)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (cardItem.imagePainter != null) {
                            Image(
                                painter = cardItem.imagePainter,
                                contentDescription = cardItem.title,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            // Show placeholder text when no image is available
                            Text(
                                text = cardItem.cardId,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                color = (Color(0xFF757575))
                            )
                        }
                    }
                }

                // Dotted Line Separator
                DottedLine(
                    modifier = Modifier
                        .width(4.dp)
                        .height(70.dp)
                        .align(Alignment.CenterVertically),
                    color = cardItem.accentColor,
                    strokeWidth = 6f,
                    dashWidth = 20f,
                    gapWidth = 8f,
                    isVertical = true
                )

                // Content Column
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(start = 10.dp, end = 10.dp),
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
        }

        Image(
            painter = getStatusIconPainter(cardItem.status),
            contentDescription = "Status Icon",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(15.dp)
                .size(20.dp)
        )
    }
}

@Composable
fun DottedLine(
    modifier: Modifier = Modifier,
    color: Color,
    strokeWidth: Float = 4f,
    dashWidth: Float = 8f,
    gapWidth: Float = 8f,
    isVertical: Boolean = true
) {
    Canvas(modifier = modifier) {
        val pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(dashWidth, gapWidth),
            phase = 0f
        )

        if (isVertical) {
            drawLine(
                color = color,
                start = Offset(size.width / 2, 0f),
                end = Offset(size.width / 2, size.height),
                strokeWidth = strokeWidth,
                pathEffect = pathEffect
            )
        } else {
            drawLine(
                color = color,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = strokeWidth,
                pathEffect = pathEffect
            )
        }
    }
}
@Composable
fun getStatusIconPainter(status: String): Painter {
    return when (status.lowercase()) {
        "pending" -> painterResource(id = R.drawable.minus_cirlce)
        "ok" -> painterResource(id = R.drawable.tick_circle)
        "bad" -> painterResource(id = R.drawable.close_circle)
        else -> painterResource(id = R.drawable.minus_cirlce) // fallback
    }
}




@Preview(showBackground = true)
@Composable
fun CardActivityAdminCardView() {
    CardActivityAdminCard(
        cardItem = CardItemAdmin(
            cardId = "231",
            title = "Sample Title",
            description = "This is a sample description for the card. It can be a bit longer to show ellipsis.",
            imagePlaceholderColor = Color(0xFFDEDEDE),
            accentColor = Color.Gray,
            status = "ok" // Try: "pending", "bad", "ok"
        )
    )
}

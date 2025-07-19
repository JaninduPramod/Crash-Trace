package com.crashtrace.mobile.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.crashtrace.mobile.R
import com.crashtrace.mobile.ui.components.AppBarMain

@Composable
fun HomeFeedScreen(navController: NavHostController) {
    var loadProfile by remember { mutableStateOf(false) }

    if (loadProfile) {
        navController.navigate("profile")
        loadProfile = false


    }

    Box(modifier = Modifier.fillMaxSize()) {

        // ✅ Background Image
        Image(
            painter = painterResource(id = R.drawable.background_image), // replace with your actual image name
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )




        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AppBarMain(
                title = "",
                BackButton = false,
                onProfileClick = { isProfile ->
                    if (isProfile) loadProfile = true
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )

            {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .background(Color(0xFFEB0404))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.red_back),
                            contentDescription = "Background Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 24.dp)
                                .offset(y = (-65).dp),
                            verticalArrangement = Arrangement.Center
                        )
                        {
                            Text(
                                text = "Bringing the World to Your Screen",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "DAILY NEWS",
                                color = Color.White,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Button(
                                onClick = { navController.navigate("mainScreen/1") },
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(48.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = Color(0xFFED0000)
                                ),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "NEWS",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Image(
                                        painter = painterResource(id = R.drawable.arrow_right),
                                        contentDescription = "Arrow",
                                        modifier = Modifier.size(20.dp),
                                        colorFilter = ColorFilter.tint(Color.Red)
                                    )
                                }
                            }
                        }
                    }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .offset(y = (-90).dp),
                ) {
                    AnalysisCard()
                    Spacer(modifier = Modifier.height(24.dp))
                    CrashTraceOptionsCard()

                    Spacer(modifier = Modifier.height(0.dp))
                }
            }
        }
    }
}

@Composable
fun AnalysisCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(388.dp)
            .shadow(20.dp, RoundedCornerShape(18.dp), ambientColor = Color.Black.copy(alpha = 0.25f)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)

        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "ANALYSIS",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4B4B4B),
                modifier = Modifier.padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Main News Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFF3636))
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cardred),
                        contentDescription = "Background",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )


                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(24.dp)
                    ) {
                        Text(
                            text = "NEWS",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "2120",
                            color = Color(0xFFF0F0F0),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Circular Progress
                    CircularProgressIndicator(
                        progress = 0.75f,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 24.dp)
                            .size(100.dp),
                        strokeColor = Color.White,
                        backgroundColor = Color(0xFFD30000).copy(alpha = 0.4f),
                        percentage = "75%"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Reports and Users Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Reports Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFF4343))
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    )
                    {
                        Image(
                            painter = painterResource(id = R.drawable.cardred2),
                            contentDescription = "Background",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(24.dp)
                        ) {
                            Text(
                                text = "Reports",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "1431",
                                color = Color.White.copy(alpha = 0.97f),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )


                        }

                        CircularProgressIndicator2(
                            progress = 0.75f,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 16.dp, bottom = 16.dp)
                                .size(52.dp),
                            strokeColor = Color.White,
                            backgroundColor = Color(0xFFC40000),
                            percentage = "75%"

                        )
                    }
                }

                // Users Card
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFF4343))
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.cardred2),
                            contentDescription = "Background",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(24.dp)
                        ) {
                            Text(
                                text = "Users",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "40321",
                                color = Color.White.copy(alpha = 0.97f),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {

                            CircularProgressIndicator2(
                                progress = 0.75f,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(end = 16.dp, top = 75.dp)
                                    .size(52.dp),
                                strokeColor = Color.White,
                                backgroundColor = Color(0xFFC40000),
                                percentage = "75%"
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun CrashTraceOptionsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(365.dp)
            .shadow(15.dp, RoundedCornerShape(18.dp), ambientColor = Color.Black.copy(alpha = 0.2f)),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)

        ) {
            Text(
                text = "Crash Trace Options",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF2B2B2B)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Option items
            OptionItem(
                icon = Icons.Default.Search,
                iconColor = Color(0xFF5872DA),
                iconBackgroundColor = Color(0xFF369FFF).copy(alpha = 0.2f),
                title = "SEARCH FOR RECORDS",
                subtitle = "Using Vehicle number"
            )

            Spacer(modifier = Modifier.height(16.dp))

            OptionItem(
                icon = Icons.Default.Edit,
                iconColor = Color(0xFFFF993A),
                iconBackgroundColor = Color(0xFFFF993A).copy(alpha = 0.2f),
                title = "REPORTING",
                subtitle = "Any time"
            )

            Spacer(modifier = Modifier.height(16.dp))

            OptionItem(
                icon = Icons.Default.Article,
                iconColor = Color(0xFF008113),
                iconBackgroundColor = Color(0xFF24BA5E).copy(alpha = 0.2f),
                title = "DAILY NEWS",
                subtitle = "Scroll Less. Learn More"
            )
        }
    }
}

@Composable
fun OptionItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    iconBackgroundColor: Color,
    title: String,
    subtitle: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(iconBackgroundColor, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF303030)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFBDBDBD)
                )
            }

            // More icon
            Icon(
                Icons.Default.MoreVert,
                contentDescription = "More",
                tint = Color(0xFF909090),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun CircularProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    strokeColor: Color = Color.White,
    backgroundColor: Color = Color.Gray,
    percentage: String = ""
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val strokeWidth = 6.dp.toPx()
            val radius = (size.minDimension - strokeWidth) / 2
            val center = Offset(size.width / 2, size.height / 2)


            drawCircle(
                color = backgroundColor,
                radius = radius,
                center = center,
                style = Stroke(width = strokeWidth)
            )


            val sweepAngle = 360 * progress
            drawArc(
                color = strokeColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                topLeft = Offset(center.x - radius, center.y - radius),
                size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
            )
        }

        if (percentage.isNotEmpty()) {
            Text(
                text = percentage,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CircularProgressIndicator2(
    progress: Float,
    modifier: Modifier = Modifier,
    strokeColor: Color = Color.White,
    backgroundColor: Color = Color.Gray,
    percentage: String = ""
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val strokeWidth = 4.dp.toPx()
            val radius = (size.minDimension - strokeWidth) / 2
            val center = Offset(size.width / 2, size.height / 2)

            // Background circle
            drawCircle(
                color = backgroundColor,
                radius = radius,
                center = center,
                style = Stroke(width = strokeWidth)
            )

            // Progress arc
            val sweepAngle = 360 * progress
            drawArc(
                color = strokeColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                topLeft = Offset(center.x - radius, center.y - radius),
                size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
            )
        }

        if (percentage.isNotEmpty()) {
            Text(
                text = percentage,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeFeedScreen() {
    val navController = rememberNavController()
    HomeFeedScreen(navController)
}

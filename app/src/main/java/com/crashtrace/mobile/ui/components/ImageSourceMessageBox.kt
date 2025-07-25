package com.crashtrace.mobile.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crashtrace.mobile.R

import androidx.compose.ui.window.Dialog
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.window.DialogProperties // Needed for DialogProperties

@Composable
fun ImageSourceSelectionDialog(
    onDismissRequest: () -> Unit,
    onCameraSelected: () -> Unit,
    onGallerySelected: () -> Unit,

    dismissOnClickOutside: Boolean = true
) {

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnClickOutside = dismissOnClickOutside,
            dismissOnBackPress = true
        )
    ) {
        Box(
            modifier = Modifier
                .width(360.dp)
                .wrapContentHeight()
                .shadow(8.dp, RoundedCornerShape(24.dp))
                .background(Color.White, shape = RoundedCornerShape(24.dp))
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color(0x1AFF2D2D))
                    .clickable { onDismissRequest() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.close_circle),
                    contentDescription = "Close",
                    modifier = Modifier.size(35.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Upload Image",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color(0xFF222222),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = "How do you upload image ?",
                    color = Color(0xFFFF2D2D),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Button(
                    onClick = onCameraSelected,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF2D2D),
                        contentColor = Color.White
                    )
                ) {
                    Text("Using The Camera")
                }
                Button(
                    onClick = onGallerySelected,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF2D2D),
                        contentColor = Color.White
                    )
                ) {
                    Text("Using The Gallery")
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PublicNewsAlertBoxPreview() { // Keeping the preview name consistent
//    ImageSourceSelectionDialog(
//        onDismissRequest = {},
//        onCameraSelected = {},
//        onGallerySelected = {},
//        dismissOnClickOutside = true // Example of using the new parameter in preview
//    )
//}
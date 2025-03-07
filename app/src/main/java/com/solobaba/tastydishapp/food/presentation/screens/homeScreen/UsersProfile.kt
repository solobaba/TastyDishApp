package com.solobaba.tastydishapp.food.presentation.screens.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.solobaba.tastydishapp.R
import com.solobaba.tastydishapp.util.SpacerVertical10Dp

@Composable
fun UsersProfile() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(R.drawable.ic_user_avatar),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.weight(1f)) //Pushes the notification icon to the right

        Icon(
            painter = painterResource(id = R.drawable.ic_notification),
            contentDescription = "Notification",
            modifier = Modifier
                .size(20.dp)
                .clickable { /* Handle notification click */ }
        )
    }

    SpacerVertical10Dp()

    Text(
        text = stringResource(R.string.hey_there_lucy),
        fontFamily = FontFamily(Font(R.font.mulish_bold)),
        fontSize = 20.sp
    )
    Text(
        text = stringResource(R.string.are_you_excited_to_create_a_tasty_dish_today),
        fontFamily = FontFamily(Font(R.font.mulish_regular)),
        fontSize = 14.sp,
        color = Color.Gray
    )
}
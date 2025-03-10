package com.solobaba.tastydishapp.food.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solobaba.tastydishapp.R
import com.solobaba.tastydishapp.ui.theme.Black1
import com.solobaba.tastydishapp.ui.theme.Grey1
import com.solobaba.tastydishapp.ui.theme.TastyDishAppTheme
import com.solobaba.tastydishapp.util.SpacerVertical10Dp
import com.solobaba.tastydishapp.util.SpacerVertical8Dp

@Composable
fun UploadFoodComponent(
    @DrawableRes image: Int,
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = Grey1,
                shape = RoundedCornerShape(4.dp)
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpacerVertical10Dp()
        Image(painter = painterResource(image), contentDescription = null)
        SpacerVertical8Dp()
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
            fontFamily = FontFamily(Font(R.font.mulish_regular)),
            color = Black1
        )
        SpacerVertical10Dp()
    }
}

@Preview
@Composable
fun UploadComponentPreview() {
    TastyDishAppTheme {
        UploadFoodComponent(image = R.drawable.ic_camera, text = "Take Photo", onClick = {})
    }
}

package com.solobaba.tastydishapp.food.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solobaba.tastydishapp.R

@Composable
fun RetryItem(
    modifier: Modifier = Modifier,
    message: String?,
    onClick: () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(24.dp)
    ) {
        Button(
            onClick = onClick,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.Black
            ),
            modifier = modifier
        ) {
            Row {
                Text(
                    text = stringResource(com.solobaba.tastydishapp.R.string.retry),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.mulish_bold))
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        message?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleSmall,
                color = Color.DarkGray,
                fontFamily = FontFamily(Font(R.font.mulish_bold))
            )
        }
    }
}

@Preview
@Composable
fun RetryItemPreview() {
    RetryItem(
        modifier = Modifier
            .height(40.dp)
            .height(100.dp), "Check your connection"
    ) {}
}
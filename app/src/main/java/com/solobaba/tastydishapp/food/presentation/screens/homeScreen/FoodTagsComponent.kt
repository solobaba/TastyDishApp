package com.solobaba.tastydishapp.food.presentation.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.solobaba.tastydishapp.ui.theme.Black1
import com.solobaba.tastydishapp.ui.theme.Pink1

@Composable
fun TagComponent(tag: String) {
    Text(
        text = tag,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier
            .background(color = Pink1, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        color = Black1
    )
}
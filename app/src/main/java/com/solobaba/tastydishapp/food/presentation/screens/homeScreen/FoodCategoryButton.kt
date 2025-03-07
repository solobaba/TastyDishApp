package com.solobaba.tastydishapp.food.presentation.screens.homeScreen

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun FoodCategoryButton(
    title: String,
    isSelected: Boolean
) {
    Button(
        onClick = { /* Handle button click */ },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isSelected) Color.Blue else Color.LightGray.copy(alpha = 0.2f)
        )
    ) {
        Text(
            title,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}
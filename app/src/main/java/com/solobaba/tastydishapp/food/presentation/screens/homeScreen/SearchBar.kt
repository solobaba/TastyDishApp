package com.solobaba.tastydishapp.food.presentation.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solobaba.tastydishapp.R

@Composable
fun SearchBar() {
    var searchFoodQuery by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        value = searchFoodQuery,
        onValueChange = { searchFoodQuery = it },
        placeholder = { Text(
            text = stringResource(R.string.search_food),
            fontFamily = FontFamily(Font(R.font.mulish_regular)),
            fontSize = 14.sp,
            color = Color.Gray
        ) },
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.LightGray.copy(alpha = 0.2f), // Optional: Light background,
                RoundedCornerShape(7.dp)
            ),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search"
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        )
    )
}
package com.solobaba.tastydishapp.food.presentation.screens.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.solobaba.tastydishapp.R
import com.solobaba.tastydishapp.food.presentation.state.FoodDishState
import com.solobaba.tastydishapp.util.SpacerVertical20Dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun FoodHomePage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        UsersProfile()
        SpacerVertical20Dp()
        SearchBar()
        SpacerVertical20Dp()

        //Food categories
        val _foodUIState = MutableStateFlow(FoodDishState())
        val foodState = _foodUIState.asStateFlow()
        ChipGroup(chipItems = foodState.collectAsState().value.categories, onItemClick = { })
        SpacerVertical20Dp()

        Text(
            text = stringResource(R.string.all_food),
            fontFamily = FontFamily(Font(R.font.mulish_bold)),
            fontSize = 20.sp
        )

        SpacerVertical20Dp()

        FoodList(navController)
    }
}
package com.solobaba.tastydishapp.food.presentation.navigation.navGraphBuilder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.solobaba.tastydishapp.food.presentation.screens.FoodScreenRoute

fun NavGraphBuilder.addFoodRoute(
    navController: NavController,
    innerPaddingValues: PaddingValues
) {
    composable(FoodScreenRoute.AddFoodScreen.route) {
        Box(modifier = Modifier.padding(innerPaddingValues)) {  }
    }
}
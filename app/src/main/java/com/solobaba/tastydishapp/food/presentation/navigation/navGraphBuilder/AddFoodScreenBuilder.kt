package com.solobaba.tastydishapp.food.presentation.navigation.navGraphBuilder

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.solobaba.tastydishapp.food.presentation.screens.FoodScreenRoute
import com.solobaba.tastydishapp.food.presentation.screens.addFoodScreen.AddFoodScreen
import com.solobaba.tastydishapp.food.presentation.screens.addFoodScreen.AddFoodScreenLayout

fun NavGraphBuilder.addFoodRoute(
    navController: NavController,
    innerPaddingValues: PaddingValues
) {
    composable(FoodScreenRoute.AddFoodScreen.route) {
        AddFoodScreen(navController)
    }
}
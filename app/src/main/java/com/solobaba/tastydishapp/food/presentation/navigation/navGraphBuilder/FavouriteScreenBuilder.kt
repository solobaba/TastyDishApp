package com.solobaba.tastydishapp.food.presentation.navigation.navGraphBuilder

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.solobaba.tastydishapp.food.presentation.screens.FoodScreenRoute
import com.solobaba.tastydishapp.food.presentation.screens.favouriteScreen.FavouriteScreen
import com.solobaba.tastydishapp.food.presentation.screens.generatorScreen.GeneratorScreen

fun NavGraphBuilder.favouriteRoute(
    navController: NavController
) {
    composable(FoodScreenRoute.FavouriteScreen.route) {
        FavouriteScreen(navController = navController)
    }
}
package com.solobaba.tastydishapp.food.presentation.navigation.navGraphBuilder

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.solobaba.tastydishapp.food.presentation.screens.FoodScreenRoute

private const val ROUTE = "foodDetailsScreen"

fun NavController.navigateToFoodDetailsScreen(foodID: Int) {
    navigate(route = "$ROUTE/$foodID")
}

private const val FOOD_ID = "foodID"

fun NavGraphBuilder.foodDetailsRoute(
    navController: NavController
) {
    composable("$ROUTE/{$FOOD_ID}",
        arguments = listOf(navArgument(FOOD_ID) {
            type = NavType.IntType
        })) { navBackStackEntry ->
        val foodID = navBackStackEntry.arguments?.getInt(FOOD_ID)
    }
}
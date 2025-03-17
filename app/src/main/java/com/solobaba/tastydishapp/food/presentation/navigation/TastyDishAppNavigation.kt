package com.solobaba.tastydishapp.food.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.compose.NavHost
import com.solobaba.tastydishapp.food.presentation.navigation.navGraphBuilder.addFoodRoute
import com.solobaba.tastydishapp.food.presentation.navigation.navGraphBuilder.favouriteRoute
import com.solobaba.tastydishapp.food.presentation.navigation.navGraphBuilder.foodDetailsRoute
import com.solobaba.tastydishapp.food.presentation.navigation.navGraphBuilder.generatorRoute
import com.solobaba.tastydishapp.food.presentation.navigation.navGraphBuilder.homeRoute
import com.solobaba.tastydishapp.food.presentation.navigation.navGraphBuilder.plannerRoute
import com.solobaba.tastydishapp.food.presentation.navigation.navGraphBuilder.searchRoute
import com.solobaba.tastydishapp.food.presentation.screens.FoodScreenRoute

@Composable
fun TastyDishAppNavigation(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController, startDestination = FoodScreenRoute.HomeScreen.route
    ) {
        homeRoute(navController, innerPadding) {}
        foodDetailsRoute(navController)
        generatorRoute(navController)
        addFoodRoute(navController, innerPadding)
        favouriteRoute(navController)
        plannerRoute(navController)
        searchRoute(navController, innerPadding)
    }
}
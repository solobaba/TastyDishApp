package com.solobaba.tastydishapp.food.presentation.navigation.navGraphBuilder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.solobaba.tastydishapp.food.domain.model.response.DomainCategory
import com.solobaba.tastydishapp.food.presentation.screens.FoodScreenRoute
import com.solobaba.tastydishapp.food.presentation.screens.homeScreen.FoodHomePage

private const val ROUTE = "homeScreen"

fun NavGraphBuilder.homeRoute(
    navController: NavController,
    innerPaddingValues: PaddingValues,
    onFoodCategoryClicked: (DomainCategory) -> Unit,
) {
    composable(FoodScreenRoute.HomeScreen.route) {
        Box(modifier = Modifier.padding(innerPaddingValues)) {
            FoodHomePage(navController, onFoodCategoryClicked)
        }
    }
}
package com.solobaba.tastydishapp.food.presentation.screens

import androidx.annotation.DrawableRes
import com.solobaba.tastydishapp.R

const val FOOD_ID = "foodID"
const val FOOD_DETAILS_SCREEN = "foodDetailsScreen"

sealed class FoodScreenRoute(
    val route: String,
    @DrawableRes val imageIcon: Int? = null
) {
    object HomeScreen: FoodScreenRoute(route = "homeScreen", imageIcon = R.drawable.ic_user_avatar)
    object FoodDetailsScreen: FoodScreenRoute(route = "foodDetailsScreen/$FOOD_ID}")
    object GeneratorScreen: FoodScreenRoute(route = "generatorScreen")
    object AddFoodScreen: FoodScreenRoute(route = "addFoodScreen")
    object FavouriteScreen: FoodScreenRoute(route = "favouriteScreen")
    object PlannerScreen: FoodScreenRoute(route = "plannerScreen")
    object SearchScreen: FoodScreenRoute(route = "searchScreen", imageIcon = R.drawable.ic_search)
}
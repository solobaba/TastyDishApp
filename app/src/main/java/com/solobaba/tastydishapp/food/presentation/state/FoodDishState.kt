package com.solobaba.tastydishapp.food.presentation.state

import com.solobaba.tastydishapp.food.domain.model.DomainAllFoodResponse


data class FoodDishState(
    var isLoading : Boolean = false,
    val isCurrentHomeScreen : Boolean = true,
    val foodListPage: Int = 1,
    val foodResponse: DomainAllFoodResponse? = null,
)

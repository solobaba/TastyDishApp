package com.solobaba.tastydishapp.food.presentation.state

import com.solobaba.tastydishapp.food.data.remote.response.Category
import com.solobaba.tastydishapp.food.domain.model.DomainFoodData

data class FoodDishState(
    var isLoading: Boolean = false,
    val isCurrentHomeScreen: Boolean = true,
    val foodListPage: Int = 1,
    val foodDataList: List<DomainFoodData> = emptyList(),
    val categories: List<Category> = emptyList(),
    val foodResponse: List<DomainFoodData> = emptyList(),
    //val foodResponse: MutableStateFlow<DomainAllFoodResponse?> = MutableStateFlow<DomainAllFoodResponse?>(null)
)
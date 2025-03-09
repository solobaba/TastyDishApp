package com.solobaba.tastydishapp.food.presentation.state

import com.solobaba.tastydishapp.food.domain.model.DomainAllFoodResponse

data class FoodDetailsState (
    var isLoading : Boolean = false,
    var domainAllFoodResponse: DomainAllFoodResponse? = null
)
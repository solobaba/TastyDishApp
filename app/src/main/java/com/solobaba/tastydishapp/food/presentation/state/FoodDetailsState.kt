package com.solobaba.tastydishapp.food.presentation.state

import com.solobaba.tastydishapp.food.domain.model.response.DomainFoodDetailsResponse

data class FoodDetailsState(
    var isLoading: Boolean = false,
    var domainAllFoodResponse: DomainFoodDetailsResponse? = null
)
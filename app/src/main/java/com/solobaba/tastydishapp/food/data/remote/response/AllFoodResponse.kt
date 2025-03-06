package com.solobaba.tastydishapp.food.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllFoodResponse(
    val data: List<FoodData>,
    val message: String,
    val status: String
)
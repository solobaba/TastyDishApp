package com.solobaba.tastydishapp.food.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodDetailsResponse(
    val data: FoodData,
    val message: String,
    val status: String
)
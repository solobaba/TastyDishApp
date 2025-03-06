package com.solobaba.tastydishapp.food.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DomainAllFoodResponse(
    val data: List<DomainFoodData>,
    val message: String,
    val status: String
)
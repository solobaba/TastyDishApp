package com.solobaba.tastydishapp.food.data.remote.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodTag(
    val id: Int,
    val name: String,
    val created_at: String,
    val updated_at: String
)
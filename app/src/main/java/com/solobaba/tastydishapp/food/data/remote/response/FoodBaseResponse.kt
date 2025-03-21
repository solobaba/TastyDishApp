package com.solobaba.tastydishapp.food.data.remote.response

data class FoodBaseResponse<T>(val data: T, val message: String, val status: String)
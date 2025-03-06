package com.solobaba.tastydishapp.food.data.remote

import com.solobaba.tastydishapp.food.data.remote.response.AllFoodResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodApiService {
    @GET("food")
    suspend fun getAllFoods(): AllFoodResponse

    @GET("food/{foodId}")
    suspend fun getFoodDetails(
        @Path("foodId") foodId: Int = 1
    ): AllFoodResponse
}
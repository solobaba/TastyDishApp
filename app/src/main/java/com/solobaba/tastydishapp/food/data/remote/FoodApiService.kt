package com.solobaba.tastydishapp.food.data.remote

import com.solobaba.tastydishapp.food.data.remote.response.AllFoodResponse
import com.solobaba.tastydishapp.food.data.remote.response.BaseResponse
import com.solobaba.tastydishapp.food.data.remote.response.Category
import com.solobaba.tastydishapp.food.data.remote.response.FoodDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Path

interface FoodApiService {
    @GET("foods")
    suspend fun getAllFoods(): AllFoodResponse

    @GET("foods/{foodId}")
    suspend fun getFoodDetails(
        @Path("foodId") foodId: Int = 1
    ): FoodDetailsResponse

    @GET("categories")
    suspend fun getFoodCategories(): BaseResponse<List<Category>>

    @Multipart
    @POST("foods")
    suspend fun addFood(

    )
}
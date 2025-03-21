package com.solobaba.tastydishapp.food.data.remote

import com.solobaba.tastydishapp.food.data.remote.response.AllFoodResponse
import com.solobaba.tastydishapp.food.data.remote.response.FoodBaseResponse
import com.solobaba.tastydishapp.food.data.remote.response.Category
import com.solobaba.tastydishapp.food.data.remote.response.FoodData
import com.solobaba.tastydishapp.food.data.remote.response.FoodDetailsResponse
import com.solobaba.tastydishapp.food.data.remote.response.FoodTag
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface FoodApiService {
    @GET("foods")
    suspend fun getAllFoods(): AllFoodResponse

    @GET("foods/{foodId}")
    suspend fun getFoodDetails(
        @Path("foodId") foodId: Int = 1
    ): FoodDetailsResponse

    @GET("categories")
    suspend fun getFoodCategories(): FoodBaseResponse<List<Category>>

    @GET("tags")
    suspend fun getFoodTags(): FoodBaseResponse<List<FoodTag>>

    @Multipart
    @POST("foods")
    suspend fun addFood(
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("category_id") categoryId: RequestBody,
        @Part("calories") calories: RequestBody,
        @Part tags: List<MultipartBody.Part>,
        @Part images: List<MultipartBody.Part>
    ): FoodBaseResponse<FoodData>
}
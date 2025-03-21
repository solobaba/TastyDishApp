package com.solobaba.tastydishapp.food.domain.repository

import com.solobaba.tastydishapp.food.domain.model.request.DomainAddFoodRequest
import com.solobaba.tastydishapp.food.domain.model.response.DomainAllFoodResponse
import com.solobaba.tastydishapp.food.domain.model.response.DomainCategory
import com.solobaba.tastydishapp.food.domain.model.response.DomainFoodData
import com.solobaba.tastydishapp.food.domain.model.response.DomainFoodDetailsResponse
import com.solobaba.tastydishapp.food.domain.model.response.DomainFoodTag
import com.solobaba.tastydishapp.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface DomainFoodRepository {
    suspend fun getAllFood(
        forceFetchFromRemote: Boolean = false
    ): Flow<ApiResult<DomainAllFoodResponse>>

    suspend fun getFoodCategories(): Flow<ApiResult<List<DomainCategory>>>

    suspend fun getFoodDetailsById(
        foodId: Int
    ): Flow<ApiResult<DomainFoodDetailsResponse>>

    suspend fun getFoodTags(): Flow<ApiResult<List<DomainFoodTag>>>

    suspend fun addFood(
        domainAddFoodRequest: DomainAddFoodRequest
    ): Flow<ApiResult<DomainFoodData>>
}
package com.solobaba.tastydishapp.food.domain.repository

import com.solobaba.tastydishapp.food.domain.model.DomainAllFoodResponse
import com.solobaba.tastydishapp.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface DomainFoodRepository {
    suspend fun getAllFood(
        forceFetchFromRemote: Boolean = false
    ): Flow<ApiResult<DomainAllFoodResponse>>

    suspend fun getFoodDetailsById(
        foodId: Int
    ): Flow<ApiResult<DomainAllFoodResponse>>
}
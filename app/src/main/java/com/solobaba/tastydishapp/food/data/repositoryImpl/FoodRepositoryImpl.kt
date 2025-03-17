package com.solobaba.tastydishapp.food.data.repositoryImpl

import com.solobaba.tastydishapp.food.data.local.FoodDatabase
import com.solobaba.tastydishapp.food.data.mapper.LocalFoodDomainFoodMapper
import com.solobaba.tastydishapp.food.data.mapper.RemoteCategoryDomainCategoryMapper
import com.solobaba.tastydishapp.food.data.mapper.RemoteFoodDomainFoodMapper
import com.solobaba.tastydishapp.food.data.mapper.RemoteFoodLocalFoodMapper
import com.solobaba.tastydishapp.food.data.remote.FoodApiService
import com.solobaba.tastydishapp.food.data.remote.response.BaseResponse
import com.solobaba.tastydishapp.food.data.remote.response.Category
import com.solobaba.tastydishapp.food.domain.model.DomainAllFoodResponse
import com.solobaba.tastydishapp.food.domain.model.DomainCategory
import com.solobaba.tastydishapp.food.domain.model.DomainFoodDetailsResponse
import com.solobaba.tastydishapp.food.domain.repository.DomainFoodRepository
import com.solobaba.tastydishapp.util.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val foodApiService: FoodApiService,
    private val foodDatabase: FoodDatabase
) : DomainFoodRepository {

    private val localFoodDomainFoodMapper: LocalFoodDomainFoodMapper = LocalFoodDomainFoodMapper()
    private val remoteFoodLocalFoodMapper: RemoteFoodLocalFoodMapper = RemoteFoodLocalFoodMapper()
    private val remoteFoodDomainFoodMapper: RemoteFoodDomainFoodMapper = RemoteFoodDomainFoodMapper()
    private val remoteCategoryDomainCategory: RemoteCategoryDomainCategoryMapper = RemoteCategoryDomainCategoryMapper()

    override suspend fun getAllFood(
        forceFetchFromRemote: Boolean
    ): Flow<ApiResult<DomainAllFoodResponse>> {
        return flow {
            emit(ApiResult.Loading(true))

            val localFoodResponse = foodDatabase.allFoodResponseDao.getAllFoodResponse()
            val shouldLoadLocalFood = localFoodResponse?.data?.isNotEmpty() == true && !forceFetchFromRemote

            if (shouldLoadLocalFood) {
                emit(ApiResult.Success(
                    data = localFoodResponse.let { allFood ->
                        localFoodDomainFoodMapper.mapLocalFoodToDomainFood(allFood)
                    }
                ))

                emit(ApiResult.Loading(false))
                return@flow
            }

            val remoteFoodResponse = try {
                foodApiService.getAllFoods()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(ApiResult.Error("Error loading Food data"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(ApiResult.Error(message = "Error loading Food data"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResult.Error(message = "Error loading Food data"))
                return@flow
            }

            val allFoodResponseEntity = remoteFoodResponse.let { remoteLocalFood ->
                remoteFoodLocalFoodMapper.mapRemoteFoodToLocalFood(remoteLocalFood)
            }

            allFoodResponseEntity?.let {
                foodDatabase.allFoodResponseDao.upsertAllFoodResponse(it)
            }

            emit(ApiResult.Success(
                data = allFoodResponseEntity.let { food ->
                    localFoodDomainFoodMapper.mapLocalFoodToDomainFood(food)
                }
            ))
            emit(ApiResult.Loading(false))
        }
    }

    override suspend fun getFoodCategories(): Flow<ApiResult<List<DomainCategory>>> {
        return flow {
            emit(ApiResult.Loading(true))

            val categories: BaseResponse<List<Category>> = try {
                foodApiService.getFoodCategories()
            } catch (e: IOException) {
                emit(ApiResult.Error("Error loading food categories"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(ApiResult.Error(message = "Error loading food categories"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResult.Error(message = "Error loading food categories"))
                return@flow
            }

            emit(ApiResult.Success(
                remoteCategoryDomainCategory.mapRemoteCategoriesToDomainCategories(categories)
            ))
            emit(ApiResult.Loading(false))
        }
    }

    override suspend fun getFoodDetailsById(
        foodId: Int
    ): Flow<ApiResult<DomainFoodDetailsResponse>> {
        return flow {
            emit(ApiResult.Loading(true))

            val getFoodDetailsById = try {
                foodApiService.getFoodDetails(foodId)
            } catch (e: IOException) {
                emit(ApiResult.Error("Error loading food"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(ApiResult.Error(message = "Error loading food"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResult.Error(message = "Error loading food"))
                return@flow
            }

            emit(ApiResult.Success(
                remoteFoodDomainFoodMapper.mapRemoteFoodToDomainFood(getFoodDetailsById)
            ))
            emit(ApiResult.Loading(false))
        }
    }
}
package com.solobaba.tastydishapp.food.data.repositoryImpl

import android.content.Context
import com.solobaba.tastydishapp.food.data.local.FoodDatabase
import com.solobaba.tastydishapp.food.data.mapper.DomainFoodRemoteFoodMapper
import com.solobaba.tastydishapp.food.data.mapper.DomainFoodTagRemoteFoodTagMapper
import com.solobaba.tastydishapp.food.data.mapper.LocalFoodDomainFoodMapper
import com.solobaba.tastydishapp.food.data.mapper.RemoteCategoryDomainCategoryMapper
import com.solobaba.tastydishapp.food.data.mapper.RemoteFoodDomainFoodMapper
import com.solobaba.tastydishapp.food.data.mapper.RemoteFoodLocalFoodMapper
import com.solobaba.tastydishapp.food.data.remote.FoodApiService
import com.solobaba.tastydishapp.food.data.remote.response.FoodBaseResponse
import com.solobaba.tastydishapp.food.data.remote.response.Category
import com.solobaba.tastydishapp.food.data.remote.response.FoodData
import com.solobaba.tastydishapp.food.data.remote.response.FoodTag
import com.solobaba.tastydishapp.food.domain.model.request.DomainAddFoodRequest
import com.solobaba.tastydishapp.food.domain.model.response.DomainAllFoodResponse
import com.solobaba.tastydishapp.food.domain.model.response.DomainCategory
import com.solobaba.tastydishapp.food.domain.model.response.DomainFoodData
import com.solobaba.tastydishapp.food.domain.model.response.DomainFoodDetailsResponse
import com.solobaba.tastydishapp.food.domain.model.response.DomainFoodTag
import com.solobaba.tastydishapp.food.domain.repository.DomainFoodRepository
import com.solobaba.tastydishapp.util.ApiResult
import com.solobaba.tastydishapp.util.getMediaTypeForFile
import com.solobaba.tastydishapp.util.uriToFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val foodApiService: FoodApiService,
    private val foodDatabase: FoodDatabase,
    @ApplicationContext private val context: Context
) : DomainFoodRepository {

    private val localFoodDomainFoodMapper: LocalFoodDomainFoodMapper = LocalFoodDomainFoodMapper()
    private val remoteFoodLocalFoodMapper: RemoteFoodLocalFoodMapper = RemoteFoodLocalFoodMapper()
    private val remoteFoodDomainFoodMapper: RemoteFoodDomainFoodMapper =
        RemoteFoodDomainFoodMapper()
    private val remoteCategoryDomainCategory: RemoteCategoryDomainCategoryMapper =
        RemoteCategoryDomainCategoryMapper()
    private val domainFoodRemoteFoodMapper: DomainFoodRemoteFoodMapper =
        DomainFoodRemoteFoodMapper()
    private val domainFoodTagRemoteFoodTagMapper: DomainFoodTagRemoteFoodTagMapper =
        DomainFoodTagRemoteFoodTagMapper()

    override suspend fun getAllFood(
        forceFetchFromRemote: Boolean
    ): Flow<ApiResult<DomainAllFoodResponse>> {
        return flow {
            emit(ApiResult.Loading(true))

            val localFoodResponse = foodDatabase.allFoodResponseDao.getAllFoodResponse()
            val shouldLoadLocalFood =
                localFoodResponse?.data?.isNotEmpty() == true && !forceFetchFromRemote

            if (shouldLoadLocalFood) {
                emit(
                    ApiResult.Success(
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

            emit(
                ApiResult.Success(
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

            val categories: FoodBaseResponse<List<Category>> = try {
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

            emit(
                ApiResult.Success(
                    remoteCategoryDomainCategory.mapRemoteCategoriesToDomainCategories(categories)
                )
            )
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

            emit(
                ApiResult.Success(
                    remoteFoodDomainFoodMapper.mapRemoteFoodToDomainFood(getFoodDetailsById)
                )
            )
            emit(ApiResult.Loading(false))
        }
    }

    override suspend fun getFoodTags(): Flow<ApiResult<List<DomainFoodTag>>> {
        return flow {
            emit(ApiResult.Loading(true))

            val getFoodTags: FoodBaseResponse<List<FoodTag>> = try {
                foodApiService.getFoodTags()
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

            emit(
                ApiResult.Success(
                    domainFoodTagRemoteFoodTagMapper.mapRemoteCategoriesToDomainCategories(getFoodTags)
                )
            )
            emit(ApiResult.Loading(false))
        }
    }

    override suspend fun addFood(
        domainAddFoodRequest: DomainAddFoodRequest
    ): Flow<ApiResult<DomainFoodData>> {
        return flow {
            emit(ApiResult.Loading(true))

            val addFood = try {
                val name = domainAddFoodRequest.name.toRequestBody("text/plain".toMediaType())
                val description =
                    domainAddFoodRequest.description.toRequestBody("text/plain".toMediaType())
                val categoryId = domainAddFoodRequest.categoryId.toString()
                    .toRequestBody("text/plain".toMediaType())
                val calories = domainAddFoodRequest.calories.toString()
                    .toRequestBody("text/plain".toMediaType())
                val tags = domainAddFoodRequest.tags.mapIndexed { index, tag ->
                    val tagRequestBody = tag.toRequestBody("text/plain".toMediaType())
                    MultipartBody.Part.createFormData("tags[$index]", null, tagRequestBody)
                }
                val images = domainAddFoodRequest.images.mapIndexed { index, uri ->
                    val imageFile = uriToFile(context = context, uri = uri)
                    MultipartBody.Part.createFormData(
                        name = "images[$index]",
                        filename = "${imageFile.name}_$index",
                        body = imageFile.asRequestBody(getMediaTypeForFile(imageFile))
                    )
                }
                foodApiService.addFood(name, description, categoryId, calories, tags, images)
            } catch (e: IOException) {
                emit(ApiResult.Error("Error creating food"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(ApiResult.Error(message = "Error creating food"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ApiResult.Error(message = "Error creating food"))
                return@flow
            }

            emit(
                ApiResult.Success(
                    domainFoodRemoteFoodMapper.mapDomainFoodToRemoteFood(addFood)
                )
            )
            emit(ApiResult.Loading(false))
        }
    }
}
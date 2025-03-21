package com.solobaba.tastydishapp.food.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solobaba.tastydishapp.food.domain.model.response.DomainCategory
import com.solobaba.tastydishapp.food.domain.model.response.DomainFoodData
import com.solobaba.tastydishapp.food.domain.repository.DomainFoodRepository
import com.solobaba.tastydishapp.food.presentation.events.FoodUiEvent
import com.solobaba.tastydishapp.food.presentation.state.FoodDishState
import com.solobaba.tastydishapp.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDishViewModel @Inject constructor(
    private val foodRepository: DomainFoodRepository
) : ViewModel() {
    private val _uiFoodState: MutableStateFlow<FoodDishState> = MutableStateFlow(FoodDishState())
    val uiFoodState: StateFlow<FoodDishState> = _uiFoodState.asStateFlow()

    private val _domainFoodResponse = MutableStateFlow<List<DomainFoodData?>>(emptyList())
    val domainFoodResponse: StateFlow<List<DomainFoodData?>> = _domainFoodResponse.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error  //Expose error state to UI

    private val foodList: List<DomainFoodData> = emptyList()

    init {
        viewModelScope.launch {
            _loading.value = true
            delay(2000) //Simulating loading state, consider removing for production
            getFoodCategories()
            getAllFoods(false)
            _loading.value = false
        }
    }

    fun onEvent(event: FoodUiEvent) {
        when (event) {
            FoodUiEvent.Navigate -> {
                _uiFoodState.update {
                    it.copy(
                        isCurrentHomeScreen = !uiFoodState.value.isCurrentHomeScreen
                    )
                }
            }

            is FoodUiEvent.Paginate -> {
                getAllFoods(true)
            }
        }
    }

    private fun getFoodCategories() {
        viewModelScope.launch {
            _uiFoodState.update {
                it.copy(isLoading = true)
            }

            foodRepository.getFoodCategories().collectLatest { categories ->
                when (categories) {
                    is ApiResult.Error -> {
                        _uiFoodState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is ApiResult.Loading -> {
                        _uiFoodState.update {
                            it.copy(isLoading = categories.isLoading)
                        }
                    }

                    is ApiResult.Success -> {
                        val updatedCategories = categories.data?.toMutableList()
                        updatedCategories?.add(
                            0,
                            DomainCategory(
                                name = "All",
                                id = 0,
                                created_at = "",
                                updated_at = "",
                                description = ""
                            )
                        )
                        _uiFoodState.update {
                            it.copy(
                                categories = updatedCategories!!,
                                currentCategory = updatedCategories.first()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getAllFoods(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _uiFoodState.update {
                it.copy(isLoading = true)
            }

            foodRepository.getAllFood(forceFetchFromRemote).collectLatest { response ->
                when (response) {
                    is ApiResult.Error -> {
                        _uiFoodState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is ApiResult.Loading -> {
                        _uiFoodState.update {
                            it.copy(isLoading = response.isLoading)
                        }
                    }

                    is ApiResult.Success -> {
                        response.data.let {
                            _domainFoodResponse.value = response.data?.data!!
                            _uiFoodState.update {
                                it.copy(
                                    foodResponse = uiFoodState.value.foodResponse,
                                    foodListPage = uiFoodState.value.foodListPage + 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun setFoodCategory(category: DomainCategory) {
        Log.d("FoodDishViewModel", "Setting category: ${category.name}")
        if (category.name.equals("All", ignoreCase = true)) {
            _uiFoodState.update {
                it.copy(
                    foodResponse = foodList, currentCategory = category
                )
            }
        } else {
            val filteredFoods = foodList.filter { food ->
                food.category.name.equals(category.name, ignoreCase = true)
            }
            _uiFoodState.update {
                it.copy(
                    foodResponse = filteredFoods, currentCategory = category
                )
            }
        }
    }
}
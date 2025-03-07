package com.solobaba.tastydishapp.food.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solobaba.tastydishapp.food.domain.model.DomainAllFoodResponse
import com.solobaba.tastydishapp.food.domain.model.DomainFoodData
import com.solobaba.tastydishapp.food.domain.repository.DomainFoodRepository
import com.solobaba.tastydishapp.food.presentation.events.FoodUiEvent
import com.solobaba.tastydishapp.food.presentation.state.FoodDishState
import com.solobaba.tastydishapp.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val _foodState = MutableStateFlow(FoodDishState())
    val foodState = _foodState.asStateFlow()

    private val _domainFoodResponse = MutableStateFlow<List<DomainFoodData?>>(emptyList())
    val domainFoodResponse: StateFlow<List<DomainFoodData?>> = _domainFoodResponse.asStateFlow()

    val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        viewModelScope.launch {
            _loading.value = true
            getAllFoods(false)
            _loading.value = false
        }
    }

    fun onEvent(event: FoodUiEvent) {
        when (event) {
            FoodUiEvent.Navigate -> {
                _foodState.update {
                    it.copy(
                        isCurrentHomeScreen = !foodState.value.isCurrentHomeScreen
                    )
                }
            }

            is FoodUiEvent.Paginate -> {
                getAllFoods(true)
            }
        }
    }

    private fun getAllFoods(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _foodState.update {
                it.copy(isLoading = true)
            }

            foodRepository.getAllFood(forceFetchFromRemote).collectLatest { response ->
                when (response) {
                    is ApiResult.Error -> {
                        _foodState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is ApiResult.Loading -> {
                        _foodState.update {
                            it.copy(isLoading = response.isLoading)
                        }
                    }

                    is ApiResult.Success -> {
                        response.data.let {
                            _domainFoodResponse.value = response.data?.data!!
                           _foodState.update {
                               it.copy(
                                   foodResponse = foodState.value.foodResponse,
                                   foodListPage = foodState.value.foodListPage + 1
                               )
                           }
                        }
                    }
                }
            }
        }
    }
}
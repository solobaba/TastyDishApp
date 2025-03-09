package com.solobaba.tastydishapp.food.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solobaba.tastydishapp.food.domain.repository.DomainFoodRepository
import com.solobaba.tastydishapp.food.presentation.state.FoodDetailsState
import com.solobaba.tastydishapp.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailsViewModel @Inject constructor(
    private val foodRepository: DomainFoodRepository
) : ViewModel() {
    var foodID: Int = 1
    private val _foodDetailsState = MutableStateFlow(FoodDetailsState())
    val foodDetailsState = _foodDetailsState.asStateFlow()

    val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        viewModelScope.launch {
            _loading.value = true
            fetchSelectedFoodDetails(foodID)
            _loading.value = false
        }
    }

    private fun fetchSelectedFoodDetails(foodID: Int) {
        viewModelScope.launch {
            _foodDetailsState.update {
                it.copy(isLoading = true)
            }

            foodRepository.getFoodDetailsById(foodID).collectLatest { response ->
                when (response) {
                    is ApiResult.Error -> {
                        _foodDetailsState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is ApiResult.Loading -> {
                        _foodDetailsState.update {
                            it.copy(isLoading = response.isLoading)
                        }
                    }

                    is ApiResult.Success -> {
                        response.data.let { foodDetails ->
                            _foodDetailsState.update {
                               it.copy(
                                   domainAllFoodResponse = foodDetails
                               )
                           }
                        }
                    }
                }
            }
        }
    }
}
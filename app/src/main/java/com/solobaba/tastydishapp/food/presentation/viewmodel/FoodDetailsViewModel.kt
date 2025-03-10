package com.solobaba.tastydishapp.food.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solobaba.tastydishapp.food.domain.model.DomainFoodData
import com.solobaba.tastydishapp.food.domain.repository.DomainFoodRepository
import com.solobaba.tastydishapp.food.presentation.state.FoodDetailsState
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
class FoodDetailsViewModel @Inject constructor(
    private val foodRepository: DomainFoodRepository
) : ViewModel() {
    var foodID: Int = 1
    private val _foodDetailsState = MutableStateFlow(FoodDetailsState())
    val foodDetailsState = _foodDetailsState.asStateFlow()

    private val _foodData = MutableStateFlow<DomainFoodData?>(null)
    val foodData: StateFlow<DomainFoodData?> = _foodData.asStateFlow()

    val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun fetchSelectedFoodDetails(foodID: Int) {
        Log.d("FoodDetailsViewModel", "Fetching details for foodID: $foodID")
        if (_foodData.value?.id == foodID) return //Prevent duplicate fetch
        this.foodID = foodID

        viewModelScope.launch {
            _foodDetailsState.update { it.copy(isLoading = true) }

            foodRepository.getFoodDetailsById(foodID).collectLatest { response ->
                when (response) {
                    is ApiResult.Error -> {
                        Log.d("FoodDetailsViewModel", "Error fetching data")
                        _foodDetailsState.update { it.copy(isLoading = false) }
                    }

                    is ApiResult.Loading -> {
                        Log.d("FoodDetailsViewModel", "Loading food details...")
                        _foodDetailsState.update { it.copy(isLoading = response.isLoading) }
                    }

                    is ApiResult.Success -> {
                        response.data?.data?.let { newData ->
                            Log.d("FoodDetailsViewModel", "Success! Data received: $newData")

                            //Ensure recomposition happens
                            _foodData.value = newData.copy()
                            _foodDetailsState.update { state ->
                                state.copy(domainAllFoodResponse = response.data)
                            }
                        }
                    }
                }
            }
        }
    }
}
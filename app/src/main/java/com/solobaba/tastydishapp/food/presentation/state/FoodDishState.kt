package com.solobaba.tastydishapp.food.presentation.state

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.solobaba.tastydishapp.food.domain.model.DomainCategory
import com.solobaba.tastydishapp.food.domain.model.DomainFoodData

data class FoodDishState(
    var isLoading: Boolean = false,
    val isCurrentHomeScreen: Boolean = true,
    val foodListPage: Int = 1,
    val categories: List<DomainCategory> = emptyList(),
    val foodResponse: List<DomainFoodData> = emptyList(),
    val foods: SnapshotStateList<DomainFoodData> = SnapshotStateList(),
    val currentCategory: DomainCategory? = null
    //val foodResponse: MutableStateFlow<DomainAllFoodResponse?> = MutableStateFlow<DomainAllFoodResponse?>(null)
)
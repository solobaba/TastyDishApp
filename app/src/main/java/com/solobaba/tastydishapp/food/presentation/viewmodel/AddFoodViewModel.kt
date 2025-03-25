package com.solobaba.tastydishapp.food.presentation.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solobaba.tastydishapp.food.data.remote.response.FoodTag
import com.solobaba.tastydishapp.food.domain.model.request.DomainAddFoodRequest
import com.solobaba.tastydishapp.food.domain.model.response.DomainCategory
import com.solobaba.tastydishapp.food.domain.model.response.DomainFoodTag
import com.solobaba.tastydishapp.food.domain.repository.DomainFoodRepository
import com.solobaba.tastydishapp.food.presentation.state.AddFoodUIState
import com.solobaba.tastydishapp.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFoodViewModel @Inject constructor(
    private val foodRepository: DomainFoodRepository
) : ViewModel() {
    private val _uiAddFoodState: MutableStateFlow<AddFoodUIState> = MutableStateFlow(AddFoodUIState())
    val uiAddFoodState: StateFlow<AddFoodUIState> = _uiAddFoodState.asStateFlow()

    val addFoodButtonState: StateFlow<Boolean> = uiAddFoodState.map {
        it.calories.isNotBlank() && it.name.isNotBlank() && it.description.isNotBlank() && it.category != null
                && it.selectedTags.isNotEmpty() && it.imageUris.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        getFoodCategories()
        getFoodTags()
    }

    fun setName(name: String) {
        _uiAddFoodState.update { it.copy(name = name) }
    }

    fun setDescription(description: String) {
        _uiAddFoodState.update { it.copy(description = description) }
    }

    fun setCategory(category: DomainCategory) {
        _uiAddFoodState.update { it.copy(category = category) }
    }

    fun setCalories(calories: String) {
        _uiAddFoodState.update { it.copy(calories = calories) }
    }

    fun setTag(tag: DomainFoodTag) {
        val currentTags: SnapshotStateList<DomainFoodTag> = SnapshotStateList()
        currentTags.addAll(uiAddFoodState.value.selectedTags)
        if (!currentTags.contains(tag)) {
            currentTags.add(tag)
            _uiAddFoodState.update {
                it.copy(selectedTags = currentTags)
            }
        }
    }

    fun deleteTag(tag: DomainFoodTag) {
        val currentTags: SnapshotStateList<DomainFoodTag> = SnapshotStateList()
        currentTags.addAll(uiAddFoodState.value.selectedTags)
        _uiAddFoodState.update {
            currentTags.remove(tag)
            it.copy(selectedTags = currentTags)
        }
    }

    fun setImageUris(uris: List<Uri>) {
        val currentUris: SnapshotStateList<Uri> = SnapshotStateList()
        currentUris.addAll(uiAddFoodState.value.imageUris)
        val urisToAdd = uris.filter { it !in currentUris }
        currentUris.addAll(urisToAdd)
        _uiAddFoodState.update {
            it.copy(imageUris = currentUris)
        }
    }

    fun deleteImageUri(uri: Uri) {
        val currentUris: SnapshotStateList<Uri> = SnapshotStateList()
        currentUris.addAll(uiAddFoodState.value.imageUris)
        _uiAddFoodState.update {
            currentUris.remove(uri)
            it.copy(imageUris = currentUris)
        }
    }

    fun addFood() {
        viewModelScope.launch {
            _uiAddFoodState.update {
                it.copy(isLoading = true)
            }

            foodRepository.addFood(
                DomainAddFoodRequest(
                    name = uiAddFoodState.value.name,
                    description = uiAddFoodState.value.description,
                    categoryId = uiAddFoodState.value.category?.id ?: 0,
                    calories = uiAddFoodState.value.calories.toInt(),
                    tags = uiAddFoodState.value.selectedTags.map { it.id.toString() },
                    images = uiAddFoodState.value.imageUris
                )).collectLatest { response ->
                when (response) {
                    is ApiResult.Error -> {
                        _uiAddFoodState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is ApiResult.Loading -> {
                        _uiAddFoodState.update {
                            it.copy(isLoading = response.isLoading)
                        }
                    }
                    is ApiResult.Success -> {
                        _uiAddFoodState.update {
                            it.copy(addFoodData = response)
                        }
                    }
                }
            }
        }
    }

    private fun getFoodTags() {
        viewModelScope.launch {
            _uiAddFoodState.update {
                it.copy(isLoading = true)
            }

            foodRepository.getFoodTags().collectLatest { foodTagsResponse ->
                when (foodTagsResponse) {
                    is ApiResult.Error -> {
                        _uiAddFoodState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is ApiResult.Loading -> {
                        _uiAddFoodState.update {
                            it.copy(isLoading = foodTagsResponse.isLoading)
                        }
                    }
                    is ApiResult.Success<*> -> {
                        _uiAddFoodState.update {
                            it.copy(tags = foodTagsResponse)
                        }
                    }
                }
            }
        }
    }

    private fun getFoodCategories() {
        viewModelScope.launch {
            _uiAddFoodState.update {
                it.copy(isLoading = true)
            }

            foodRepository.getFoodCategories().collectLatest { categories ->
                when (categories) {
                    is ApiResult.Error -> {
                        _uiAddFoodState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is ApiResult.Loading -> {
                        _uiAddFoodState.update {
                            it.copy(isLoading = categories.isLoading)
                        }
                    }
                    is ApiResult.Success -> {
                        val categoryList = categories.data?.toMutableList()
                        _uiAddFoodState.update {
                            it.copy(
                                categories = categoryList!!
                            )
                        }
                    }
                }
            }
        }
    }
}
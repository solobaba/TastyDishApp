package com.solobaba.tastydishapp.food.presentation.state

import android.net.Uri
import com.solobaba.tastydishapp.food.data.remote.response.Category
import com.solobaba.tastydishapp.food.data.remote.response.FoodTag
import com.solobaba.tastydishapp.food.domain.model.response.DomainCategory
import com.solobaba.tastydishapp.food.domain.model.response.DomainFoodTag

sealed class AddFoodScreenLayoutActions {
    data class OnNameChange(val name: String) : AddFoodScreenLayoutActions()
    data class OnDescriptionChange(val description: String) : AddFoodScreenLayoutActions()
    data class OnCategorySelected(val category: DomainCategory) : AddFoodScreenLayoutActions()
    data class OnCaloriesChange(val calories: String) : AddFoodScreenLayoutActions()
    data class OnTagSelected(val tag: DomainFoodTag) : AddFoodScreenLayoutActions()
    data object OnBackClick : AddFoodScreenLayoutActions()
    data object OnCreateClick : AddFoodScreenLayoutActions()
    data object OnTakePhotoClick : AddFoodScreenLayoutActions()
    data object OnUploadClick : AddFoodScreenLayoutActions()
    data class OnDeleteTag(val tag: DomainFoodTag) : AddFoodScreenLayoutActions()
    data class OnDeleteImage(val uri: Uri) : AddFoodScreenLayoutActions()
}
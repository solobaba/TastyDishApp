package com.solobaba.tastydishapp.food.presentation.state

import android.net.Uri
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.solobaba.tastydishapp.food.domain.model.response.DomainCategory
import com.solobaba.tastydishapp.food.domain.model.response.DomainFoodData
import com.solobaba.tastydishapp.food.domain.model.response.DomainFoodTag
import com.solobaba.tastydishapp.util.ApiResult

data class AddFoodUIState(
    val name: String = "",
    val description: String = "",
    val category: DomainCategory? = null,
    val calories: String = "",
    val selectedTags: SnapshotStateList<DomainFoodTag> = SnapshotStateList(),
    val isLoading: Boolean = false,
    val addFoodData: ApiResult<DomainFoodData>? = null,
    val categories: List<DomainCategory> = emptyList(),
    val tags: ApiResult<List<DomainFoodTag>>? = null,
    val imageUris: SnapshotStateList<Uri> = SnapshotStateList()
)
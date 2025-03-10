package com.solobaba.tastydishapp.food.data.remote.response

import android.net.Uri
import androidx.compose.runtime.snapshots.SnapshotStateList

data class AddFoodData(
    val name: String = "",
    val description: String = "",
    val category: Category? = null,
    val calories: String = "",
    val selectedTags: SnapshotStateList<Tag> = SnapshotStateList(),
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val imageUris: SnapshotStateList<Uri> = SnapshotStateList()
)
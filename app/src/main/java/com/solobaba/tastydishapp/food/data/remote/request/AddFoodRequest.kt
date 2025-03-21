package com.solobaba.tastydishapp.food.data.remote.request

import android.net.Uri

data class AddFoodRequest(
    val name: String,
    val description: String,
    val categoryId: Int,
    val calories: Int,
    val tags: List<String>,
    val images: List<Uri>
)
package com.solobaba.tastydishapp.food.domain.model.request

import android.net.Uri

data class DomainAddFoodRequest(
    val name: String,
    val description: String,
    val categoryId: Int,
    val calories: Int,
    val tags: List<String>,
    val images: List<Uri>
)
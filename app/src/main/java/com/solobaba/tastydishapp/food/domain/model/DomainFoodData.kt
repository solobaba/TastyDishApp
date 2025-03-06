package com.solobaba.tastydishapp.food.domain.model

data class DomainFoodData(
    val calories: Int,
    val category: DomainCategory,
    val category_id: Int,
    val created_at: String,
    val description: String,
    val foodImages: List<DomainFoodImage>,
    val foodTags: List<String>,
    val id: Int,
    val name: String,
    val updated_at: String
)

package com.solobaba.tastydishapp.food.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class FoodData(
    val calories: Int,
    val category: Category,
    val category_id: Int,
    val created_at: String,
    val description: String,
    val foodImages: List<FoodImage>,
    val foodTags: List<String>,
    val id: Int,
    val name: String,
    val updated_at: String
): Parcelable

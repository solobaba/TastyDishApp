package com.solobaba.tastydishapp.food.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class FoodImage(
    val id: Int,
    val image_url: String
): Parcelable

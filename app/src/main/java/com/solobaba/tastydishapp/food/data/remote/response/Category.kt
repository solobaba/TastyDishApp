package com.solobaba.tastydishapp.food.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class Category(
    val created_at: String,
    val description: String,
    val id: Int,
    val name: String,
    val updated_at: String
): Parcelable

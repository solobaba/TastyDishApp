package com.solobaba.tastydishapp.food.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.solobaba.tastydishapp.food.data.remote.response.FoodData

@Entity(tableName = "AllFoodResponse")
data class AllFoodResponseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val data: List<FoodData>,
    val message: String,
    val status: String
)

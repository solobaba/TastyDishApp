package com.solobaba.tastydishapp.food.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface AllFoodResponseDao {
    @Upsert
    suspend fun upsertAllFoodResponse(allFoodResponseEntity: AllFoodResponseEntity)

    @Query("SELECT * FROM AllFoodResponse")
    suspend fun getAllFoodResponse(): AllFoodResponseEntity
}
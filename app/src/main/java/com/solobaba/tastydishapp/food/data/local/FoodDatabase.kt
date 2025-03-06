package com.solobaba.tastydishapp.food.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.solobaba.tastydishapp.util.DataTypeConverter

@Database(
    entities = [AllFoodResponseEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(DataTypeConverter::class)
abstract class FoodDatabase : RoomDatabase() {
    abstract val allFoodResponseDao: AllFoodResponseDao
}
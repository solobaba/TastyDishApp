package com.solobaba.tastydishapp.util

import androidx.room.TypeConverter
import com.solobaba.tastydishapp.food.data.remote.response.FoodData
import com.solobaba.tastydishapp.food.data.remote.response.FoodImage
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object DataTypeConverter {
    @TypeConverter
    @JvmStatic
    fun foodDataStringToList(data: String?): List<FoodData> {
        if (data == null) {
            return emptyList()
        }

        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, FoodData::class.java)
        val adapter = moshi.adapter<List<FoodData>>(type)
        return adapter.fromJson(data) ?: emptyList()
    }

    @TypeConverter
    @JvmStatic
    fun foodDataListToString(objects: List<FoodData>): String {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, FoodData::class.java)
        val adapter = moshi.adapter<List<FoodData>>(type)
        return adapter.toJson(objects)
    }

    @TypeConverter
    @JvmStatic
    fun foodImageToString(data: String?): List<FoodImage> {
        if (data == null) {
            return emptyList()
        }

        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, FoodImage::class.java)
        val adapter = moshi.adapter<List<FoodImage>>(type)
        return adapter.fromJson(data) ?: emptyList()
    }

    @TypeConverter
    @JvmStatic
    fun foodImageListToString(objects: List<FoodImage>): String {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, FoodImage::class.java)
        val adapter = moshi.adapter<List<FoodImage>>(type)
        return adapter.toJson(objects)
    }

    @TypeConverter
    @JvmStatic
    fun foodTagsToList(data: String?): List<String> {
        if (data == null) {
            return emptyList()
        }

        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(type)
        return adapter.fromJson(data) ?: emptyList()
    }

    @TypeConverter
    @JvmStatic
    fun foodTagsListToString(objects: List<String>): String {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(type)
        return adapter.toJson(objects)
    }
}
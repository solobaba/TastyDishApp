package com.solobaba.tastydishapp.food.data.mapper

import com.solobaba.tastydishapp.food.data.local.AllFoodResponseEntity
import com.solobaba.tastydishapp.food.data.remote.response.AllFoodResponse
import com.solobaba.tastydishapp.food.data.remote.response.Category
import com.solobaba.tastydishapp.food.data.remote.response.FoodData
import com.solobaba.tastydishapp.food.data.remote.response.FoodImage

class RemoteFoodLocalFoodMapper {
    fun mapRemoteFoodToLocalFood(allFoodResponse: AllFoodResponse?): AllFoodResponseEntity? {
        if (allFoodResponse == null) return null
        return with(allFoodResponse) {
            AllFoodResponseEntity(
                data = data.map { it.toFoodData() },
                message = message,
                status = status
            )
        }
    }

    fun FoodData.toFoodData(): FoodData {
        return FoodData(
            calories = calories,
            category = category.toCategory(),
            category_id = category_id,
            created_at = created_at,
            description = description,
            foodImages = foodImages.map { it.toFoodImages() },
            foodTags = foodTags,
            id = id,
            name = name,
            updated_at = updated_at
        )
    }

    fun Category.toCategory(): Category {
        return Category(
            created_at = created_at,
            description = description,
            id = id,
            name = name,
            updated_at = updated_at
        )
    }

    fun FoodImage.toFoodImages(): FoodImage {
        return FoodImage(
            id = id,
            image_url = image_url,
        )
    }
}
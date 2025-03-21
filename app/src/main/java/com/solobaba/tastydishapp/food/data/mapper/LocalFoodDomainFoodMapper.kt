package com.solobaba.tastydishapp.food.data.mapper

import com.solobaba.tastydishapp.food.data.local.AllFoodResponseEntity
import com.solobaba.tastydishapp.food.data.remote.response.Category
import com.solobaba.tastydishapp.food.data.remote.response.FoodData
import com.solobaba.tastydishapp.food.data.remote.response.FoodImage
import com.solobaba.tastydishapp.food.domain.model.response.DomainAllFoodResponse
import com.solobaba.tastydishapp.food.domain.model.response.DomainCategory
import com.solobaba.tastydishapp.food.domain.model.response.DomainFoodData
import com.solobaba.tastydishapp.food.domain.model.response.DomainFoodImage

class LocalFoodDomainFoodMapper {
    fun mapLocalFoodToDomainFood(allFoodResponseEntity: AllFoodResponseEntity?): DomainAllFoodResponse? {
        if (allFoodResponseEntity == null) return null
        return with(allFoodResponseEntity) {
            DomainAllFoodResponse(
                data = data.map { it.toDomainFoodData() },
                message = message,
                status = status
            )
        }
    }

    fun FoodData.toDomainFoodData(): DomainFoodData {
        return DomainFoodData(
            calories = calories,
            category = category.toDomainCategory(),
            category_id = category_id,
            created_at = created_at,
            description = description,
            foodImages = foodImages.map { it.toDomainFoodImages() },
            foodTags = foodTags,
            id = id,
            name = name,
            updated_at = updated_at
        )
    }

    fun Category.toDomainCategory(): DomainCategory {
        return DomainCategory(
            created_at = created_at,
            description = description,
            id = id,
            name = name,
            updated_at = updated_at
        )
    }

    fun FoodImage.toDomainFoodImages(): DomainFoodImage {
        return DomainFoodImage(
            id = id,
            image_url = image_url,
        )
    }
}
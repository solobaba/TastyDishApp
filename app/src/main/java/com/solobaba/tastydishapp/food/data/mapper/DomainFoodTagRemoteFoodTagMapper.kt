package com.solobaba.tastydishapp.food.data.mapper

import com.solobaba.tastydishapp.food.data.remote.response.FoodBaseResponse
import com.solobaba.tastydishapp.food.data.remote.response.FoodTag
import com.solobaba.tastydishapp.food.domain.model.response.DomainFoodTag

class DomainFoodTagRemoteFoodTagMapper {
    fun mapRemoteCategoriesToDomainCategories(foodTag: FoodBaseResponse<List<FoodTag>>?): List<DomainFoodTag>? {
        return foodTag?.data?.map { tag ->
            DomainFoodTag(
                id = tag.id,
                name = tag.name,
                created_at = tag.created_at,
                updated_at = tag.updated_at
            )
        }
    }
}
package com.solobaba.tastydishapp.food.data.mapper

import com.solobaba.tastydishapp.food.data.remote.response.FoodBaseResponse
import com.solobaba.tastydishapp.food.data.remote.response.Category
import com.solobaba.tastydishapp.food.domain.model.response.DomainCategory

class RemoteCategoryDomainCategoryMapper {
    fun mapRemoteCategoriesToDomainCategories(categories: FoodBaseResponse<List<Category>>?): List<DomainCategory>? {
        return categories?.data?.map { category ->
            DomainCategory(
                created_at = category.created_at,
                description = category.description,
                id = category.id,
                name = category.name,
                updated_at = category.updated_at
            )
        }
    }
}
package com.solobaba.tastydishapp.food.di

import com.solobaba.tastydishapp.food.data.repositoryImpl.FoodRepositoryImpl
import com.solobaba.tastydishapp.food.domain.repository.DomainFoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFoodRepository(
        foodRepositoryImpl: FoodRepositoryImpl
    ): DomainFoodRepository

}
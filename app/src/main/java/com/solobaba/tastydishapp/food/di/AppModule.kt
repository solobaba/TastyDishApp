package com.solobaba.tastydishapp.food.di

import android.app.Application
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.solobaba.tastydishapp.food.data.local.FoodDatabase
import com.solobaba.tastydishapp.food.data.remote.Constants
import com.solobaba.tastydishapp.food.data.remote.FoodApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val moshi: Moshi? = null

    private fun provideMoshi(): Moshi {
        synchronized(this) {
            return moshi ?: Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        }
    }

    private fun getClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideFoodApiService(): FoodApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(getClient())
            .build()
            .create(FoodApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFoodDatabase(app: Application): FoodDatabase {
        return Room.databaseBuilder(
            app,
            FoodDatabase::class.java,
            "foodDb.db"
        ).build()
    }

//    @Provides
//    @Singleton
//    fun provideDomainFoodRepository(
//        foodRepositoryImpl: FoodRepositoryImpl
//    ): DomainFoodRepository = foodRepositoryImpl
}
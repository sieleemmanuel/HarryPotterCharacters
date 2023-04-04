package com.sielehub.harrypottercharacters.di

import com.sielehub.harrypottercharacters.data.api.ApiService
import com.sielehub.harrypottercharacters.data.repo.MainRepository
import com.sielehub.harrypotterdata.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesApiService(): ApiService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRepository(apiService:ApiService) = MainRepository(apiService)

}
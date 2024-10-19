package com.towitty.bookreport.di

import com.towitty.bookreport.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModules {

    @Singleton
    @Provides
    fun provideDispatcherIO() = Dispatchers.IO

    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        converterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://openapi.naver.com/v1/search/")
        .addConverterFactory(converterFactory)
        .build()

    @Singleton
    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService = retrofit.create(ApiService::class.java)
}

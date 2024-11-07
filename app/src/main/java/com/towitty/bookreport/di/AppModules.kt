package com.towitty.bookreport.di

import android.content.Context
import androidx.room.Room
import com.towitty.bookreport.data.local.BookReportDatabase
import com.towitty.bookreport.data.local.TagDao
import com.towitty.bookreport.data.local.TagLocalRepository
import com.towitty.bookreport.data.local.TagRepository
import com.towitty.bookreport.data.network.ApiService
import com.towitty.bookreport.data.network.BookRemoteRepository
import com.towitty.bookreport.data.network.IBookDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideBookReportDatabase(
        @ApplicationContext context: Context
    ): BookReportDatabase = Room
        .databaseBuilder(context, BookReportDatabase::class.java, "book_report_db")
        .build()

    @Singleton
    @Provides
    fun provideTagDao(appDatabase: BookReportDatabase): TagDao = appDatabase.tagDao()

    @Singleton
    @Provides
    fun provideTagRepository(tagDao: TagDao): TagRepository = TagLocalRepository(tagDao)

    @Singleton
    @Provides
    fun provideBookRemoteRepository(dataSource: IBookDataSource): BookRemoteRepository =
        BookRemoteRepository(dataSource)
}

package com.towitty.bookreport.data.database.di

import com.towitty.bookreport.data.database.BookReportDatabase
import com.towitty.bookreport.data.database.TagDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DaosModule {

    @Singleton
    @Provides
    fun provideTagDao(appDatabase: BookReportDatabase): TagDao = appDatabase.tagDao()

}
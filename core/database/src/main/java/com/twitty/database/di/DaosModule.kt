package com.twitty.database.di

import com.twitty.database.BookReportDatabase
import com.twitty.database.dao.TagDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DaosModule {

    @Provides
    fun provideTagDao(appDatabase: BookReportDatabase): TagDao = appDatabase.tagDao()

    @Provides
    fun provideBookDao(appDatabase: BookReportDatabase) = appDatabase.bookDao()

    @Provides
    fun provideBookReportDao(appDatabase: BookReportDatabase) = appDatabase.bookReportDao()

}
package com.towitty.bookreport.data.database.di

import com.towitty.bookreport.data.database.BookReportDatabase
import com.towitty.bookreport.data.database.TagDao
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
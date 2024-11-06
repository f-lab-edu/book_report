package com.towitty.bookreport.di

import android.content.Context
import androidx.room.Room
import com.towitty.bookreport.data.local.BookReportDatabase
import com.towitty.bookreport.data.local.TagDao
import com.towitty.bookreport.data.local.TagLocalRepository
import com.towitty.bookreport.data.local.TagRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

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
}
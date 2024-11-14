package com.towitty.bookreport.data.database.di

import android.content.Context
import androidx.room.Room
import com.towitty.bookreport.data.database.BookReportDatabase
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
    ): BookReportDatabase = Room.databaseBuilder(
        context,
        BookReportDatabase::class.java,
        "book_report_db"
    ).build()

}
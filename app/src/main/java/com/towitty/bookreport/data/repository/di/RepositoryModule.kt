package com.towitty.bookreport.data.repository.di

import com.towitty.bookreport.data.repository.BookReportRepository
import com.towitty.bookreport.data.repository.BookRepository
import com.towitty.bookreport.data.repository.IBookReportRepository
import com.towitty.bookreport.data.repository.IBookRepository
import com.towitty.bookreport.data.repository.ITagRepository
import com.towitty.bookreport.data.repository.TagLocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindsTagRepository(tagLocalRepository: TagLocalRepository): ITagRepository

    @Binds
    abstract fun bindsBookRepository(bookRepository: BookRepository): IBookRepository

    @Binds
    abstract fun bindsBookReportRepository(bookReportRepository: BookReportRepository): IBookReportRepository
}

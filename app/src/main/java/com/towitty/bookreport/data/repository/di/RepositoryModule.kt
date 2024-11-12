package com.towitty.bookreport.data.repository.di

import com.towitty.bookreport.data.repository.BookRemoteRepository
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
    abstract fun bindsBookRepository(bookRemoteRepository: BookRemoteRepository): IBookRepository
}

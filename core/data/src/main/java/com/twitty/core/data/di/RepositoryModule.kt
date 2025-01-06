package com.twitty.core.data.di

import com.twitty.core.data.repository.BookReportRepository
import com.twitty.core.data.repository.BookRepository
import com.twitty.core.data.repository.FavoritesRepository
import com.twitty.core.data.repository.IBookReportRepository
import com.twitty.core.data.repository.IBookRepository
import com.twitty.core.data.repository.IFavoritesRepository
import com.twitty.core.data.repository.ITagRepository
import com.twitty.core.data.repository.TagLocalRepository
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

    @Binds
    abstract fun bindsFavoritesRepository(favoritesRepository: FavoritesRepository): IFavoritesRepository
}

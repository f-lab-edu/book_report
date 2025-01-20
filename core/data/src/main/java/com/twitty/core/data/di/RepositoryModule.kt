package com.twitty.core.data.di

import com.twitty.core.data.repository.BookReportRepository
import com.twitty.core.data.repository.BookRepository
import com.twitty.core.data.repository.FavoritesRepository
import com.twitty.core.data.repository.IBookReportRepository
import com.twitty.core.data.repository.IBookRepository
import com.twitty.core.data.repository.IFavoritesRepository
import com.twitty.core.data.repository.IRecommendedBooksRepository
import com.twitty.core.data.repository.ITagRepository
import com.twitty.core.data.repository.RecommendedBooksRepository
import com.twitty.core.data.repository.TagRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindsTagRepository(tagRepository: TagRepository): ITagRepository

    @Binds
    abstract fun bindsBookRepository(bookRepository: BookRepository): IBookRepository

    @Binds
    abstract fun bindsBookReportRepository(bookReportRepository: BookReportRepository): IBookReportRepository

    @Binds
    abstract fun bindsFavoritesRepository(favoritesRepository: FavoritesRepository): IFavoritesRepository

    @Binds
    abstract fun bindsRecommendedBooksRepository(recommendedBooksRepository: RecommendedBooksRepository): IRecommendedBooksRepository
}

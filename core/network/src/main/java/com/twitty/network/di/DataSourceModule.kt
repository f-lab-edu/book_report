package com.twitty.network.di

import com.twitty.network.retrofit.BookRemoteDataSource
import com.twitty.network.retrofit.IBookDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {

    @Binds
    abstract fun bindsBookDataSource(bookRemoteDataSource: BookRemoteDataSource): IBookDataSource
}
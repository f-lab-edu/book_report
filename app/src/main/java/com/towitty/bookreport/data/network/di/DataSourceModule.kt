package com.towitty.bookreport.data.network.di

import com.towitty.bookreport.data.network.BookRemoteDataSource
import com.towitty.bookreport.data.network.IBookDataSource
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
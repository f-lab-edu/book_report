package com.towitty.bookreport.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
object DispatchersModule {

    @Dispatcher(BookReportDispatchers.IO)
    @Provides
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Dispatcher(BookReportDispatchers.Default)
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

}
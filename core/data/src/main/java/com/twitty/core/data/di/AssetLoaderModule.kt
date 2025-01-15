package com.twitty.core.data.di

import android.content.Context
import com.twitty.core.data.AssetLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AssetLoaderModule {
    @Provides
    @Singleton
    fun provideAssetLoader(@ApplicationContext context: Context): AssetLoader {
        return AssetLoader(context)
    }
}
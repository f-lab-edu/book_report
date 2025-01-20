package com.twitty.core.data.di

import com.twitty.core.data.AssetLoader
import com.twitty.core.data.AssetLoaderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class AssetLoaderModule {

    @Binds
    abstract fun bindAssetLoader( impl: AssetLoaderImpl): AssetLoader

}
package com.ircarren.ghkanban.di

import com.ircarren.ghkanban.data.repository.RepositoryImpl
import com.ircarren.ghkanban.data.repository.RepositoryNetworkImpl
import com.ircarren.ghkanban.domain.Repository
import com.ircarren.ghkanban.domain.RepositoryNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providerRepository(repository: RepositoryImpl): Repository
    @Binds
    abstract fun providerRepositoryNetWork(repository: RepositoryNetworkImpl): RepositoryNetwork
}
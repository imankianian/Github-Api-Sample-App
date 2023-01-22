package com.example.taskb.di

import com.example.taskb.repository.remote.datasource.RemoteDataSource
import com.example.taskb.repository.remote.datasource.RemoteDataSourceImpl
import com.example.taskb.repository.repository.Repository
import com.example.taskb.repository.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractModule {

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}
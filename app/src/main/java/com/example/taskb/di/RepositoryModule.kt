package com.example.taskb.di

import com.example.taskb.repository.remote.RemoteDataSource
import com.example.taskb.repository.remote.RemoteDataSourceImpl
import com.example.taskb.repository.Repository
import com.example.taskb.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUsersRemoteDataSource(usersRemoteDataSourceImpl: RemoteDataSourceImpl):
            RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindUsersRepository(repositoryImpl: RepositoryImpl): Repository
}
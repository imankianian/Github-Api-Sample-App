package com.example.taskb.di

import com.example.taskb.data.repository.remote.UsersRemoteDataSource
import com.example.taskb.data.repository.remote.UsersRemoteDataSourceImpl
import com.example.taskb.data.repository.UsersRepository
import com.example.taskb.data.repository.UsersRepositoryImpl
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
    abstract fun bindUsersRemoteDataSource(usersRemoteDataSourceImpl: UsersRemoteDataSourceImpl):
            UsersRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindUsersRepository(usersRepositoryImpl: UsersRepositoryImpl): UsersRepository
}
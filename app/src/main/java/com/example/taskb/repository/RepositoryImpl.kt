package com.example.taskb.repository

import com.example.taskb.repository.remote.RemoteDataSource
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource): Repository {

    override suspend fun getUsers() = remoteDataSource.getUsers()
    override suspend fun getUserRepos(login: String) = remoteDataSource.getUserRepos(login)
}
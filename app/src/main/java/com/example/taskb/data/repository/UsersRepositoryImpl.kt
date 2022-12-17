package com.example.taskb.data.repository

import com.example.taskb.data.repository.remote.UsersRemoteDataSource
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(private val usersRemoteDataSource: UsersRemoteDataSource): UsersRepository {

    override suspend fun getUsers() = usersRemoteDataSource.getUsers()
    override suspend fun getUserRepos(login: String) = usersRemoteDataSource.getUserRepos(login)
}
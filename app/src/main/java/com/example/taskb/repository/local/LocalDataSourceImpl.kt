package com.example.taskb.repository.local

import com.example.taskb.repository.local.model.LocalUser
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val gitHubDatabase: GitHubDatabase): LocalDataSource {

    override suspend fun getUsers() = gitHubDatabase.userDao.getUsers()
    override suspend fun saveUsers(users: List<LocalUser>) = gitHubDatabase.userDao.insertAll(users)
}
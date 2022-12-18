package com.example.taskb.repository.remote

import com.example.taskb.repository.remote.model.Repo
import com.example.taskb.repository.remote.model.User

interface RemoteDataSource {

    suspend fun getUsers(): ApiResult<List<User>>
    suspend fun getUserRepos(login: String): ApiResult<List<Repo>>
}
package com.example.taskb.data.repository.remote

import com.example.taskb.data.model.Repo
import com.example.taskb.data.model.User

interface UsersRemoteDataSource {

    suspend fun getUsers(): ApiResult<List<User>>
    suspend fun getUserRepos(login: String): ApiResult<List<Repo>>
}
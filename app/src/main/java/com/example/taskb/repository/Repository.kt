package com.example.taskb.repository

import com.example.taskb.repository.remote.model.Repo
import com.example.taskb.repository.remote.ApiResult
import com.example.taskb.repository.remote.model.User

interface Repository {

    suspend fun getUsers(): ApiResult<List<User>>
    suspend fun getUserRepos(login: String): ApiResult<List<Repo>>
}
package com.example.taskb.data.repository

import com.example.taskb.data.model.Repo
import com.example.taskb.data.repository.remote.ApiResult
import com.example.taskb.data.model.User

interface UsersRepository {

    suspend fun getUsers(): ApiResult<List<User>>
    suspend fun getUserRepos(login: String): ApiResult<List<Repo>>
}
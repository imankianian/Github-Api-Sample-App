package com.example.taskb.repository.remote

import com.example.taskb.ApiResult

interface RemoteDataSource {

    suspend fun getUsers(): ApiResult
    suspend fun getUserRepos(login: String): ApiResult
}
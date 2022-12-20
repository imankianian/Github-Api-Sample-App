package com.example.taskb.repository.remote

import com.example.taskb.ApiResult
import com.example.taskb.repository.remote.model.RemoteRepo
import com.example.taskb.repository.remote.model.RemoteUser

interface RemoteDataSource {

    suspend fun getUsers(): ApiResult<List<RemoteUser>>
    suspend fun getUserRepos(login: String): ApiResult<List<RemoteRepo>>
}
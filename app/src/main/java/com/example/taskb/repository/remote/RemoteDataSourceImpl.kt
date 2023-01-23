package com.example.taskb.repository.remote

import com.example.taskb.ApiResult
import com.example.taskb.di.IoDispatcher
import com.example.taskb.repository.remote.api.GitHubApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val gitHubApi: GitHubApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher): RemoteDataSource {

    override suspend fun getUsers(): ApiResult = withContext(dispatcher) {
        try {
            val response = gitHubApi.getUsers()
            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            ApiResult.Exception(e)
        }
    }

    override suspend fun getUserRepos(login: String): ApiResult = withContext(dispatcher) {
        try {
            val response = gitHubApi.getUserRepos(login)
            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            ApiResult.Exception(e)
        }
    }
}
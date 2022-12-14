package com.example.taskb.repository.remote

import com.example.taskb.ApiResult
import com.example.taskb.repository.remote.model.RemoteRepo
import com.example.taskb.repository.remote.api.GitHubApi
import com.example.taskb.repository.remote.model.RemoteUser
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val gitHubApi: GitHubApi): RemoteDataSource {

    override suspend fun getUsers(): ApiResult<List<RemoteUser>> {
        return try {
            val response = gitHubApi.getUsers()
            return if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            ApiResult.Exception(e)
        }
    }

    override suspend fun getUserRepos(login: String): ApiResult<List<RemoteRepo>> {
        return try {
            val response = gitHubApi.getUserRepos(login)
            return if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            ApiResult.Exception(e)
        }
    }
}
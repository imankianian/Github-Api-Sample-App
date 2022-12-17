package com.example.taskb.data.repository.remote

import com.example.taskb.data.model.Repo
import com.example.taskb.data.repository.remote.api.GitHubApi
import com.example.taskb.data.model.User
import javax.inject.Inject

class UsersRemoteDataSourceImpl @Inject constructor(private val gitHubApi: GitHubApi): UsersRemoteDataSource {

    override suspend fun getUsers(): ApiResult<List<User>> {
        return try {
            val response = gitHubApi.getUsers()
            return if (response.isSuccessful && response.body() != null) {
                ApiResult.ApiSuccess(response.body()!!)
            } else {
                ApiResult.ApiError(response.code(), response.message())
            }
        } catch (e: Exception) {
            ApiResult.ApiException(e)
        }
    }

    override suspend fun getUserRepos(login: String): ApiResult<List<Repo>> {
        return try {
            val response = gitHubApi.getUserRepos(login)
            return if (response.isSuccessful && response.body() != null) {
                ApiResult.ApiSuccess(response.body()!!)
            } else {
                ApiResult.ApiError(response.code(), response.message())
            }
        } catch (e: Exception) {
            ApiResult.ApiException(e)
        }
    }
}
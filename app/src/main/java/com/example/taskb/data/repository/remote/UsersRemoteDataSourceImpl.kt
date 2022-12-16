package com.example.taskb.data.repository.remote

import com.example.taskb.data.repository.remote.api.GitHubApi
import com.example.taskb.data.model.User
import javax.inject.Inject

class UsersRemoteDataSourceImpl @Inject constructor(private val gitHubApi: GitHubApi): UsersRemoteDataSource {

    override suspend fun getUsers(): UsersApiResult<List<User>> {
        return try {
            val response = gitHubApi.getUsers()
            return if (response.isSuccessful && response.body() != null) {
                UsersApiResult.ApiSuccess(response.body()!!)
            } else {
                UsersApiResult.ApiError(response.code(), response.message())
            }
        } catch (e: Exception) {
            UsersApiResult.ApiException(e)
        }
    }
}
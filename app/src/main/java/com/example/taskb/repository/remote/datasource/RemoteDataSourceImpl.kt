package com.example.taskb.repository.remote.datasource

import com.example.taskb.repository.remote.api.GitHubApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val gitHubApi: GitHubApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO):RemoteDataSource {

    override suspend fun getUsers(): NetworkResult = withContext(dispatcher) {
        try {
            val response = gitHubApi.fetchUsers()
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Error(response.code(), response.message())
            }
        } catch (exception: Exception) {
            NetworkResult.Failure(exception.message ?: "Exception")
        }
    }
}
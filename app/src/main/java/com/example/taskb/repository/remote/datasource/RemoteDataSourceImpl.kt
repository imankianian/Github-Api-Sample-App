package com.example.taskb.repository.remote.datasource

import com.example.taskb.di.IoDispatcher
import com.example.taskb.repository.remote.api.GitHubApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(private val gitHubApi: GitHubApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher):RemoteDataSource {

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
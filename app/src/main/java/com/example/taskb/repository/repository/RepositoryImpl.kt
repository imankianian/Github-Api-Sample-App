package com.example.taskb.repository.repository

import com.example.taskb.di.IoDispatcher
import com.example.taskb.repository.remote.datasource.NetworkResult
import com.example.taskb.repository.remote.datasource.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher): Repository {

    override suspend fun getUsers(): UserResult = withContext(dispatcher) {
        when (val result = remoteDataSource.getUsers()) {
            is NetworkResult.Success -> {
                return@withContext UserResult.Success(result.users)
            }
            is NetworkResult.Error -> {
                return@withContext UserResult.Failure("${result.code}: ${result.message}")
            }
            is NetworkResult.Failure -> {
                return@withContext UserResult.Failure("${result.message}")
            }
        }
    }
}
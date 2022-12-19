package com.example.taskb.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskb.ApiResult
import com.example.taskb.DataResult
import com.example.taskb.repository.local.LocalDataSource
import com.example.taskb.repository.remote.RemoteDataSource
import com.example.taskb.TAG
import com.example.taskb.remoteUserToLocalUser

import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource): Repository {

    private val _dataResult = MutableLiveData<DataResult>(DataResult.Loading)
    override val dataResult: LiveData<DataResult>
        get() = _dataResult

    override suspend fun loadUsers() {
        Log.d(TAG, "loadUsers() called")
        val users = localDataSource.getUsers()
        if (users.isNotEmpty()) {
            Log.d(TAG, "Loaded data from DB")
            _dataResult.value = DataResult.Success(users)
        } else {
            Log.d(TAG, "Loading data from network")
            when(val result = remoteDataSource.getUsers()) {
                is ApiResult.Success -> {
                    Log.d(TAG, "Loading network data => Success: ${result.data.size}")
                    val newUsers = result.data.remoteUserToLocalUser()
                    localDataSource.saveUsers(newUsers)
                    _dataResult.value = DataResult.Success(newUsers)
                }
                is ApiResult.Error -> {
                    Log.d(TAG, "Loading network data => Error: ${result.message}")
                    _dataResult.value = DataResult.Error(result.message ?: result.code.toString())
                }
                is ApiResult.Exception -> {
                    Log.d(TAG, "Loading network data => Exception: ${result.throwable.localizedMessage}")
                    _dataResult.value = DataResult.Error(result.throwable.localizedMessage ?: "Exception")
                }
            }
        }
    }

    override suspend fun getUserRepos(login: String) = remoteDataSource.getUserRepos(login)
}
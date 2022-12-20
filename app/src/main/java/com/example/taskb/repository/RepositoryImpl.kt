package com.example.taskb.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.taskb.*
import com.example.taskb.repository.local.LocalDataSource
import com.example.taskb.repository.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource): Repository {

    private val _users = MutableLiveData<UsersResult>(UsersResult.Loading)
    override val users: LiveData<UsersResult>
        get() = _users

    private val _repos = MutableLiveData<ReposResult>(ReposResult.Loading)
    override val repos: LiveData<ReposResult>
        get() = _repos

    override suspend fun loadUsers() {
        withContext(Dispatchers.IO) {
            Log.d(TAG, "loadUsers() called")
            val dbUsers = localDataSource.getUsers()
            if (dbUsers.isNotEmpty()) {
                Log.d(TAG, "Loaded users from DB")
                _users.postValue(UsersResult.Success(dbUsers))
            } else {
                Log.d(TAG, "Loading users from network")
                when(val result = remoteDataSource.getUsers()) {
                    is ApiResult.Success -> {
                        Log.d(TAG, "Loading network users => Success: ${result.data.size}")
                        val newUsers = result.data.remoteUserToLocalUser()
                        localDataSource.saveUsers(newUsers)
                        _users.postValue(UsersResult.Success(newUsers))
                    }
                    is ApiResult.Error -> {
                        Log.d(TAG, "Loading network users => Error: ${result.message}")
                        _users.postValue(UsersResult.Error(result.message ?: result.code.toString()))
                    }
                    is ApiResult.Exception -> {
                        Log.d(TAG, "Loading network users => Exception: ${result.throwable.localizedMessage}")
                        _users.postValue(UsersResult.Error(result.throwable.localizedMessage ?: "Exception"))
                    }
                }
            }
        }
    }

    override suspend fun loadUserRepos(login: String) {
        withContext(Dispatchers.IO) {
            Log.d(TAG, "loadUserRepos() called")
            val user = localDataSource.getUser(login)
            if (user.reposSaved) {
                Log.d(TAG, "Loading repo from DB")
                val userRepos = localDataSource.getUserRepos(login)
                _repos.postValue(ReposResult.Success(userRepos))
            } else {
                Log.d(TAG, "Loading repo from network")
                when(val result = remoteDataSource.getUserRepos(login)) {
                    is ApiResult.Success -> {
                        Log.d(TAG, "Loading network repo => Success: ${result.data.size}")
                        val newRepos = result.data.remoteRepoToLocalRepo(login)
                        localDataSource.saveUserRepos(login, newRepos)
                        _repos.postValue(ReposResult.Success(newRepos))
                    }
                    is ApiResult.Error -> {
                        Log.d(TAG, "Loading network repo => Error: ${result.message}")
                        _repos.postValue(ReposResult.Error(result.message ?: result.code.toString()))
                    }
                    is ApiResult.Exception -> {
                        Log.d(TAG, "Loading network repo => Exception: ${result.throwable.localizedMessage}")
                        _repos.postValue(ReposResult.Error(result.throwable.localizedMessage ?: "Exception"))
                    }
                }
            }
        }
    }
}
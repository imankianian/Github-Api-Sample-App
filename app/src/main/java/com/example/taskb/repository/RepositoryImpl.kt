package com.example.taskb.repository

import android.util.Log
import com.example.taskb.*
import com.example.taskb.repository.local.LocalDataSource
import com.example.taskb.repository.remote.RemoteDataSource
import com.example.taskb.repository.remote.model.RemoteRepo
import com.example.taskb.repository.remote.model.RemoteUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource): Repository {

    override suspend fun loadUsers(): UsersResult = withContext(Dispatchers.IO) {
        Log.d(TAG, "loadUsers() called")
        val dbUsers = localDataSource.getUsers()
        if (dbUsers.isNotEmpty()) {
            Log.d(TAG, "Loaded users from DB")
            UsersResult.Success(dbUsers)
        } else {
            getUsersFromApi()
        }
    }

    override suspend fun loadUserRepos(login: String): ReposResult = withContext(Dispatchers.IO) {
        Log.d(TAG, "loadUserRepos() called")
        val user = localDataSource.getUser(login)
        if (user.reposSaved) {
            Log.d(TAG, "Loading repo from DB")
            val userRepos = localDataSource.getUserRepos(login)
            ReposResult.Success(userRepos)
        } else {
            getReposFromApi(login)
        }
    }

    private suspend fun getUsersFromApi(): UsersResult {
        Log.d(TAG, "Loading users from network")
        return when(val result = remoteDataSource.getUsers()) {
            is ApiResult.Success<*> -> {
                (result.data as? List<RemoteUser>)!!.let { remoteUsers ->
                    Log.d(TAG, "Loading network users => Success: ${remoteUsers.size}")
                    val localUsers = remoteUsers.remoteUserToLocalUser()
                    localDataSource.saveUsers(localUsers)
                    return@let UsersResult.Success(localUsers)
                }
            }
            is ApiResult.Error -> {
                Log.d(TAG, "Loading network users => Error: ${result.message}")
                UsersResult.Error(result.message ?: result.code.toString())
            }
            is ApiResult.Exception -> {
                Log.d(TAG, "Loading network users => Exception: ${result.throwable.localizedMessage}")
                UsersResult.Error(result.throwable.localizedMessage ?: "Exception")
            }
        }
    }

    private suspend fun getReposFromApi(login: String): ReposResult {
        Log.d(TAG, "Loading repo from network")
        return when(val result = remoteDataSource.getUserRepos(login)) {
            is ApiResult.Success<*> -> {
                (result.data as? List<RemoteRepo>)!!.let { remoteRepos ->
                    Log.d(TAG, "Loading network repo => Success: ${remoteRepos.size}")
                    val localRepos = remoteRepos.remoteRepoToLocalRepo(login)
                    localDataSource.saveUserRepos(login, localRepos)
                    ReposResult.Success(localRepos)
                }
            }
            is ApiResult.Error -> {
                Log.d(TAG, "Loading network repo => Error: ${result.message}")
                ReposResult.Error(result.message ?: result.code.toString())
            }
            is ApiResult.Exception -> {
                Log.d(TAG, "Loading network repo => Exception: ${result.throwable.localizedMessage}")
                ReposResult.Error(result.throwable.localizedMessage ?: "Exception")
            }
        }
    }
}
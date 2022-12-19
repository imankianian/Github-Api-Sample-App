package com.example.taskb.repository

import androidx.lifecycle.LiveData
import com.example.taskb.ReposResult
import com.example.taskb.UsersResult

interface Repository {

    val users: LiveData<UsersResult>
    val repos: LiveData<ReposResult>

    suspend fun loadUsers()
    suspend fun loadUserRepos(login: String)
}
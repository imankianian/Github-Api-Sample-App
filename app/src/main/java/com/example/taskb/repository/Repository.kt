package com.example.taskb.repository

import com.example.taskb.ReposResult
import com.example.taskb.UsersResult

interface Repository {

    suspend fun loadUsers(): UsersResult
    suspend fun loadUserRepos(login: String): ReposResult
}
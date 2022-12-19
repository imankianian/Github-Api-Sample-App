package com.example.taskb.repository.local

import com.example.taskb.repository.local.model.LocalRepo
import com.example.taskb.repository.local.model.LocalUser

interface LocalDataSource {

    suspend fun getUsers(): List<LocalUser>
    suspend fun saveUsers(users: List<LocalUser>)
    suspend fun getUserRepos(login: String): List<LocalRepo>
    suspend fun saveUserRepos(repos: List<LocalRepo>)
}
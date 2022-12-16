package com.example.taskb.data.repository.remote

import com.example.taskb.data.model.User

interface UsersRemoteDataSource {

    suspend fun getUsers(): UsersApiResult<List<User>>
}
package com.example.taskb.data.repository

import com.example.taskb.data.repository.remote.UsersApiResult
import com.example.taskb.data.model.User

interface UsersRepository {

    suspend fun getUsers(): UsersApiResult<List<User>>
}
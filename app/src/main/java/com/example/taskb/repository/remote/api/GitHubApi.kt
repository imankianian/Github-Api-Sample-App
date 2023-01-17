package com.example.taskb.repository.remote.api

import com.example.taskb.repository.remote.model.User
import retrofit2.Response
import retrofit2.http.GET

interface GitHubApi {

    @GET("users")
    suspend fun fetchUsers(): Response<List<User>>
}
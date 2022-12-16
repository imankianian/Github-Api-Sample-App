package com.example.taskb.data.repository.remote.api

import com.example.taskb.data.model.User
import retrofit2.Response
import retrofit2.http.GET

interface GitHubApi {

    @GET("/users")
    suspend fun getUsers(): Response<List<User>>
}
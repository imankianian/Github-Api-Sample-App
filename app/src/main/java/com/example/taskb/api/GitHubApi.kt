package com.example.taskb.api

import com.example.taskb.model.User
import retrofit2.Response
import retrofit2.http.GET

interface GitHubApi {

    @GET("/users")
    suspend fun getUsers(): Response<List<User>>
}
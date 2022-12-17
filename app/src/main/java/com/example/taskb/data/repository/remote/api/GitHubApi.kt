package com.example.taskb.data.repository.remote.api

import com.example.taskb.data.model.Repo
import com.example.taskb.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {

    @GET("/users")
    suspend fun getUsers(): Response<List<User>>

    @GET("/users/{login}/repos")
    suspend fun getUserRepos(@Path("login") login: String): Response<List<Repo>>
}
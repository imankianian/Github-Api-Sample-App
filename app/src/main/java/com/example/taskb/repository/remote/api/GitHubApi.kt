package com.example.taskb.repository.remote.api

import com.example.taskb.repository.remote.model.Repo
import com.example.taskb.repository.remote.model.RemoteUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {

    @GET("/users")
    suspend fun getUsers(): Response<List<RemoteUser>>

    @GET("/users/{login}/repos")
    suspend fun getUserRepos(@Path("login") login: String): Response<List<Repo>>
}
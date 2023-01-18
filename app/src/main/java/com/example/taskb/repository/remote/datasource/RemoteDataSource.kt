package com.example.taskb.repository.remote.datasource

interface RemoteDataSource {

    suspend fun getUsers(): NetworkResult
}
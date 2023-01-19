package com.example.taskb.repository.repository

interface Repository {

    suspend fun getUsers(): UserResult
}
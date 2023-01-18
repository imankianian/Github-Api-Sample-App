package com.example.taskb.repository.remote.datasource

import com.example.taskb.repository.remote.model.User

sealed interface NetworkResult {
    data class Success(val users: List<User>): NetworkResult
    data class Error(val code: Int, val message: String?): NetworkResult
    data class Failure(val message: String?): NetworkResult
}
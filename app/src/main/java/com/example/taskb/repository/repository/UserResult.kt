package com.example.taskb.repository.repository

import com.example.taskb.repository.remote.model.User

sealed interface UserResult {
    data class Success(val users: List<User>): UserResult
    data class Failure(val message: String?): UserResult
}
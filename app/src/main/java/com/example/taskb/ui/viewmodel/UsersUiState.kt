package com.example.taskb.ui.viewmodel

import com.example.taskb.repository.remote.model.User

sealed interface UsersUiState {
    object Loading: UsersUiState
    data class Success(val users: List<User>): UsersUiState
    data class Failure(val message: String?): UsersUiState
}
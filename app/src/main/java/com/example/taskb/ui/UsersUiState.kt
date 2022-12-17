package com.example.taskb.ui

import com.example.taskb.data.model.User

sealed interface UsersUiState {

    data class Success(val users: List<User>): UsersUiState
    object Loading: UsersUiState
    class Error(val message: String): UsersUiState
}
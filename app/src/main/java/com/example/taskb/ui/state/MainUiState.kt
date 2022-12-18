package com.example.taskb.ui.state

import com.example.taskb.repository.remote.model.User

sealed interface MainUiState {

    data class Success(val users: List<User>): MainUiState
    object Loading: MainUiState
    data class Error(val message: String): MainUiState
}
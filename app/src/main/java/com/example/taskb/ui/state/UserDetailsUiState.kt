package com.example.taskb.ui.state

import com.example.taskb.repository.remote.model.Repo

sealed interface UserDetailsUiState {

    data class Success(val repos: List<Repo>): UserDetailsUiState
    object Loading: UserDetailsUiState
    data class Error(val message: String): UserDetailsUiState
}
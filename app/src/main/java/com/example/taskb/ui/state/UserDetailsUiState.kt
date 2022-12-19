package com.example.taskb.ui.state

import com.example.taskb.repository.local.model.LocalRepo

sealed interface UserDetailsUiState {

    data class Success(val localRepos: List<LocalRepo>): UserDetailsUiState
    object Loading: UserDetailsUiState
    data class Error(val message: String): UserDetailsUiState
}
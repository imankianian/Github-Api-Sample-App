package com.example.taskb.ui.state

import com.example.taskb.repository.local.model.LocalUser

sealed interface MainUiState {

    data class Success(val remoteUsers: List<LocalUser>): MainUiState
    object Loading: MainUiState
    data class Error(val message: String): MainUiState
}
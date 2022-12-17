package com.example.taskb.ui

import com.example.taskb.data.model.Repo

sealed interface ReposUiState {

    data class Success(val repos: List<Repo>): ReposUiState
    object Loading: ReposUiState
    data class Error(val message: String): ReposUiState
}
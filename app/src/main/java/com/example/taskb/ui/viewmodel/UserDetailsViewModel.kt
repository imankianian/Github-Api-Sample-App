package com.example.taskb.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskb.repository.Repository
import com.example.taskb.repository.remote.ApiResult
import com.example.taskb.ui.state.UserDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val DETAILS_VIEW_MODEL = "DETAILS_VIEW_MODEL"

@HiltViewModel
class UserDetailsViewModel @Inject constructor(private val repository: Repository,
                                           savedStateHandle: SavedStateHandle): ViewModel() {

    private val login = savedStateHandle.get<String>("login")
    var userDetailsUiState: UserDetailsUiState by mutableStateOf(UserDetailsUiState.Loading)
        private set

    init {
       login?.let { listUserRepos(login) }
    }

    private fun listUserRepos(login: String) {
        viewModelScope.launch {
            when(val result = repository.getUserRepos(login)) {
                is ApiResult.ApiSuccess -> {
                    userDetailsUiState = UserDetailsUiState.Success(result.data)
                    Log.d(DETAILS_VIEW_MODEL, "listRepos => Success, list size:${result.data.size}")
                }
                is ApiResult.ApiError -> {
                    userDetailsUiState = UserDetailsUiState.Error(result.message ?: result.code.toString())
                    Log.d(DETAILS_VIEW_MODEL, "listRepos => Error: ${result.code}, ${result.message}")
                }
                is ApiResult.ApiException -> {
                    userDetailsUiState = UserDetailsUiState.Error(result.throwable.localizedMessage ?: "Exception")
                    Log.d(DETAILS_VIEW_MODEL, "listRepos => Exception: ${result.throwable.message}")
                }
            }
        }
    }
}
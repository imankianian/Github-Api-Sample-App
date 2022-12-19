package com.example.taskb.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskb.ApiResult
import com.example.taskb.TAG
import com.example.taskb.repository.Repository
import com.example.taskb.ui.state.UserDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

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
                is ApiResult.Success -> {
                    userDetailsUiState = UserDetailsUiState.Success(result.data)
                    Log.d(TAG, "listRepos => Success, list size:${result.data.size}")
                }
                is ApiResult.Error -> {
                    userDetailsUiState = UserDetailsUiState.Error(result.message ?: result.code.toString())
                    Log.d(TAG, "listRepos => Error: ${result.code}, ${result.message}")
                }
                is ApiResult.Exception -> {
                    userDetailsUiState = UserDetailsUiState.Error(result.throwable.localizedMessage ?: "Exception")
                    Log.d(TAG, "listRepos => Exception: ${result.throwable.message}")
                }
            }
        }
    }
}
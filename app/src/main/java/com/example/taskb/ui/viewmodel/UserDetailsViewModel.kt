package com.example.taskb.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskb.ReposResult
import com.example.taskb.TAG
import com.example.taskb.convert
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
            userDetailsUiState = UserDetailsUiState.Loading
            repository.loadUserRepos(login)
            when(val result = repository.repos.value!!) {
                is ReposResult.Loading -> {
                    userDetailsUiState = UserDetailsUiState.Loading
                }
                is ReposResult.Success -> {
                    userDetailsUiState = UserDetailsUiState.Success(result.users.convert())
                    Log.d(TAG, "listRepos => Success, list size:${result.users.size}")
                }
                is ReposResult.Error -> {
                    userDetailsUiState = UserDetailsUiState.Error(result.message)
                    Log.d(TAG, "listRepos => Error: ${result.message}")
                }
            }
        }
    }
}
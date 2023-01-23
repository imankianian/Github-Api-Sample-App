package com.example.taskb.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskb.ReposResult
import com.example.taskb.TAG
import com.example.taskb.convert
import com.example.taskb.repository.Repository
import com.example.taskb.ui.state.UserDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(private val repository: Repository,
                                           savedStateHandle: SavedStateHandle): ViewModel() {

    val login = savedStateHandle.get<String>("login")
    private var _userDetailsUiState = MutableStateFlow<UserDetailsUiState>(UserDetailsUiState.Loading)
    val usersDetailsUiState: StateFlow<UserDetailsUiState> = _userDetailsUiState
    private var connectionLost = false

    init {
       login?.let { listUserRepos(login) }
    }

    private fun listUserRepos(login: String) {
        viewModelScope.launch {
            _userDetailsUiState.value = UserDetailsUiState.Loading
            when(val result = repository.loadUserRepos(login)) {
                is ReposResult.Success -> {
                    connectionLost = false
                    _userDetailsUiState.value = UserDetailsUiState.Success(result.users.convert())
                    Log.d(TAG, "listRepos => Success, list size:${result.users.size}")
                }
                is ReposResult.Error -> {
                    _userDetailsUiState.value = UserDetailsUiState.Error(result.message)
                    Log.d(TAG, "listRepos => Error: ${result.message}")
                    connectionLost = true
                    delay(5000)
                    if (connectionLost) {
                        listUserRepos(login)
                    }
                }
            }
        }
    }
}
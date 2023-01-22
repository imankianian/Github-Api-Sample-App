package com.example.taskb.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskb.repository.repository.Repository
import com.example.taskb.repository.repository.UserResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _usersUiState = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val usersUiState: StateFlow<UsersUiState> = _usersUiState

    fun getUsers() {
        viewModelScope.launch {
            when (val result = repository.getUsers()) {
                is UserResult.Success -> {
                    _usersUiState.value = UsersUiState.Success(result.users)
                }
                is UserResult.Failure -> {
                    _usersUiState.value = UsersUiState.Failure(result.message)
                }
            }
        }
    }
}
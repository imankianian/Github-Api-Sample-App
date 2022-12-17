package com.example.taskb.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskb.data.repository.UsersRepository
import com.example.taskb.data.repository.remote.ApiResult
import com.example.taskb.ui.UsersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val USERS_VIEWMODEL = "USERS_VIEWMODEL"

@HiltViewModel
class UsersViewModel @Inject constructor(private val usersRepository: UsersRepository): ViewModel() {

    var usersUiState: UsersUiState by mutableStateOf(UsersUiState.Loading)
        private set

    init {
        listUsers()
    }

    private fun listUsers() {
        viewModelScope.launch {
            usersUiState = UsersUiState.Loading
            when (val result = usersRepository.getUsers()) {
                is ApiResult.ApiSuccess -> {
                    usersUiState = UsersUiState.Success(result.data)
                    Log.d(USERS_VIEWMODEL, "listUsers => Success, list size:${result.data.size}")
                }
                is ApiResult.ApiError -> {
                    usersUiState = UsersUiState.Error(result.message ?: result.code.toString())
                    Log.d(USERS_VIEWMODEL, "listUsers => Error: ${result.code}, ${result.message}")
                }
                is ApiResult.ApiException -> {
                    usersUiState = UsersUiState.Error(result.throwable.localizedMessage ?: "Exception")
                    Log.d(USERS_VIEWMODEL, "listUsers => Exception: ${result.throwable.message}")
                }
            }
        }
    }
}
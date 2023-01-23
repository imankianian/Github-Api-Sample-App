package com.example.taskb.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskb.UsersResult
import com.example.taskb.TAG
import com.example.taskb.repository.Repository
import com.example.taskb.ui.state.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    var mainUiState: MainUiState by mutableStateOf(MainUiState.Loading)
        private set

    private var connectionLost = false

    init {
        listUsers()
    }

    private fun listUsers() {
        viewModelScope.launch {
            when (val result = repository.loadUsers()) {
                is UsersResult.Loading -> {
                    mainUiState = MainUiState.Loading
                }
                is UsersResult.Success -> {
                    connectionLost = false
                    mainUiState = MainUiState.Success(result.users)
                    Log.d(TAG, "listUsers => Success, list size:${result.users.size}")
                }
                is UsersResult.Error -> {
                    mainUiState = MainUiState.Error(result.message)
                    Log.d(TAG, "listUsers => Error: ${result.message}")
                    connectionLost = true
                    delay(5000)
                    if (connectionLost) {
                        listUsers()
                    }
                }
            }
        }
    }
}
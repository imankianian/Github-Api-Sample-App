package com.example.taskb.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskb.UsersResult
import com.example.taskb.TAG
import com.example.taskb.repository.Repository
import com.example.taskb.ui.state.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private var _mainUiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val mainUiState: StateFlow<MainUiState> = _mainUiState

    private var connectionLost = false

    init {
        listUsers()
    }

    private fun listUsers() {
        viewModelScope.launch {
            when (val result = repository.loadUsers()) {
                is UsersResult.Success -> {
                    connectionLost = false
                    _mainUiState.value = MainUiState.Success(result.users)
                    Log.d(TAG, "listUsers => Success, list size:${result.users.size}")
                }
                is UsersResult.Error -> {
                    _mainUiState.value = MainUiState.Error(result.message)
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
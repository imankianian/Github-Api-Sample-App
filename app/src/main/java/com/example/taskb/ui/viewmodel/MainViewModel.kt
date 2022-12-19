package com.example.taskb.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskb.DataResult
import com.example.taskb.TAG
import com.example.taskb.repository.Repository
import com.example.taskb.ui.state.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    var mainUiState: MainUiState by mutableStateOf(MainUiState.Loading)
        private set

    init {
        listUsers()
    }

    private fun listUsers() {
        viewModelScope.launch {
            mainUiState = MainUiState.Loading
            repository.loadUsers()
            when (val result = repository.dataResult.value!!) {
                is DataResult.Loading -> {
                    mainUiState = MainUiState.Loading
                }
                is DataResult.Success -> {
                    mainUiState = MainUiState.Success(result.users)
                    Log.d(TAG, "listUsers => Success, list size:${result.users.size}")
                }
                is DataResult.Error -> {
                    mainUiState = MainUiState.Error(result.message)
                    Log.d(TAG, "listUsers => Error: ${result.message}")
                }
            }
        }
    }
}
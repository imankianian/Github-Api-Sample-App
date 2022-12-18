package com.example.taskb.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskb.repository.Repository
import com.example.taskb.repository.remote.ApiResult
import com.example.taskb.ui.state.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val MAIN_VIEW_MODEL = "MAIN_VIEW_MODEL"

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
            when (val result = repository.getUsers()) {
                is ApiResult.ApiSuccess -> {
                    mainUiState = MainUiState.Success(result.data)
                    Log.d(MAIN_VIEW_MODEL, "listUsers => Success, list size:${result.data.size}")
                }
                is ApiResult.ApiError -> {
                    mainUiState = MainUiState.Error(result.message ?: result.code.toString())
                    Log.d(MAIN_VIEW_MODEL, "listUsers => Error: ${result.code}, ${result.message}")
                }
                is ApiResult.ApiException -> {
                    mainUiState = MainUiState.Error(result.throwable.localizedMessage ?: "Exception")
                    Log.d(MAIN_VIEW_MODEL, "listUsers => Exception: ${result.throwable.message}")
                }
            }
        }
    }
}
package com.example.taskb.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskb.data.repository.UsersRepository
import com.example.taskb.data.repository.remote.ApiResult
import com.example.taskb.ui.ReposUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val REPO_VIEWMODEL = "REPO_VIEWMODEL"

@HiltViewModel
class UserReposViewModel @Inject constructor(private val usersRepository: UsersRepository,
                                             savedStateHandle: SavedStateHandle): ViewModel() {

    private val login = savedStateHandle.get<String>("login")
    var reposUiState: ReposUiState by mutableStateOf(ReposUiState.Loading)
        private set

    init {
       login?.let { listUserRepos(login) }
    }

    private fun listUserRepos(login: String) {
        viewModelScope.launch {
            when(val result = usersRepository.getUserRepos(login)) {
                is ApiResult.ApiSuccess -> {
                    reposUiState = ReposUiState.Success(result.data)
                    Log.d(REPO_VIEWMODEL, "listRepos => Success, list size:${result.data.size}")
                }
                is ApiResult.ApiError -> {
                    reposUiState = ReposUiState.Error(result.message ?: result.code.toString())
                    Log.d(REPO_VIEWMODEL, "listRepos => Error: ${result.code}, ${result.message}")
                }
                is ApiResult.ApiException -> {
                    reposUiState = ReposUiState.Error(result.throwable.localizedMessage ?: "Exception")
                    Log.d(REPO_VIEWMODEL, "listRepos => Exception: ${result.throwable.message}")
                }
            }
        }
    }
}
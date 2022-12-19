package com.example.taskb.repository

import androidx.lifecycle.LiveData
import com.example.taskb.ApiResult
import com.example.taskb.DataResult
import com.example.taskb.repository.remote.model.Repo

interface Repository {

    val dataResult: LiveData<DataResult>

    suspend fun loadUsers()
    suspend fun getUserRepos(login: String): ApiResult<List<Repo>>
}
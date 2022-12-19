package com.example.taskb

import com.example.taskb.repository.local.model.LocalUser
import com.example.taskb.repository.remote.model.RemoteUser

const val TAG = "TASK_B"

sealed interface ApiResult<T: Any> {
    class Success<T: Any>(val data: T): ApiResult<T>
    class Error<T: Any>(val code: Int, val message: String?): ApiResult<T>
    class Exception<T: Any>(val throwable: Throwable): ApiResult<T>
}

sealed interface DataResult {
    data class Success(val users: List<LocalUser>): DataResult
    object Loading: DataResult
    data class Error(val message: String): DataResult
}

fun List<RemoteUser>.remoteUserToLocalUser(): List<LocalUser> {
    val localUsers = mutableListOf<LocalUser>()
    this.forEach { remoteUser ->
        localUsers.add(LocalUser(login = remoteUser.login, avatarUrl = remoteUser.avatarUrl))
    }
    return localUsers
}


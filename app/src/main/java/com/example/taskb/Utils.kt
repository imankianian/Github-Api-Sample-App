package com.example.taskb

import com.example.taskb.repository.local.model.LocalRepo
import com.example.taskb.repository.local.model.LocalUser
import com.example.taskb.repository.remote.model.RemoteRepo
import com.example.taskb.repository.remote.model.RemoteUser
import java.text.SimpleDateFormat

const val TAG = "TASK_B"

sealed interface ApiResult<T: Any> {
    class Success<T: Any>(val data: T): ApiResult<T>
    class Error<T: Any>(val code: Int, val message: String?): ApiResult<T>
    class Exception<T: Any>(val throwable: Throwable): ApiResult<T>
}

sealed interface UsersResult {
    data class Success(val users: List<LocalUser>): UsersResult
    object Loading: UsersResult
    data class Error(val message: String): UsersResult
}

sealed interface ReposResult {
    data class Success(val users: List<LocalRepo>): ReposResult
    object Loading: ReposResult
    data class Error(val message: String): ReposResult
}

fun List<RemoteUser>.remoteUserToLocalUser(): List<LocalUser> {
    val localUsers = mutableListOf<LocalUser>()
    this.forEach { remoteUser ->
        localUsers.add(LocalUser(login = remoteUser.login, avatarUrl = remoteUser.avatarUrl))
    }
    return localUsers
}

fun List<RemoteRepo>.remoteRepoToLocalRepo(login: String): List<LocalRepo> {
    val localRepos = mutableListOf<LocalRepo>()
    this.forEach { remoteRepo ->
        localRepos.add(LocalRepo(name = remoteRepo.name, lastUpdate = remoteRepo.lastUpdate,
            stars = remoteRepo.stars, language = remoteRepo.language, htmlUrl = remoteRepo.htmlUrl,
            login = login))
    }
    return localRepos
}

fun List<LocalRepo>.convert(): List<LocalRepo> = map {
    LocalRepo(
        id = it.id,
        name = it.name,
        lastUpdate = it.lastUpdate.formattedDate(),
        stars = it.stars,
        language = it.language,
        htmlUrl = it.htmlUrl,
        login = it.login
    )
}

fun String.formattedDate(): String {
    return try {
        val oldFormat = SimpleDateFormat("yyyy-mm-dd")
        val date = oldFormat.parse(this)
        val newFormat = SimpleDateFormat("mm-dd-yyyy")
        newFormat.format(date)
    } catch (e: Exception) {
        this
    }
}
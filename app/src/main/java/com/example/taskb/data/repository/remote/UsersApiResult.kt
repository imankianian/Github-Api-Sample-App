package com.example.taskb.data.repository.remote

sealed interface UsersApiResult<T: Any> {

    class ApiSuccess<T: Any>(val data: T): UsersApiResult<T>
    class ApiError<T: Any>(val code: Int, val message: String?): UsersApiResult<T>
    class ApiException<T: Any>(val throwable: Throwable): UsersApiResult<T>
}

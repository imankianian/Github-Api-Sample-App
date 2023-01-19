package com.example.taskb.repository.repository

import com.example.taskb.repository.remote.api.GitHubApi
import com.example.taskb.repository.remote.datasource.RemoteDataSource
import com.example.taskb.repository.remote.datasource.RemoteDataSourceImpl
import com.example.taskb.repository.remote.model.User
import com.github.javafaker.Faker
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

class RepositoryIntegrationTest {

    private val gitHubApi: GitHubApi = mock()
    private val remoteDataSource: RemoteDataSource = Mockito.spy(RemoteDataSourceImpl(gitHubApi))
    private val repository: Repository = RepositoryImpl(remoteDataSource)

    @Test
    fun getUsersCallsRemoteDataSourceGetUsers() = runTest {
        repository.getUsers()
        verify(remoteDataSource).getUsers()
    }

    @Test
    fun getUsersReturnsListOfUsersIfRetrievedSuccessfully() = runTest {
        val faker = Faker()
        val login = faker.name().username()
        val avatarUrl = faker.internet().avatar()
        val user = User(login, avatarUrl)
        whenever(gitHubApi.fetchUsers()).thenReturn(Response.success(listOf(user)))
        val result = repository.getUsers()
        assertEquals(user, (result as UserResult.Success).users[0])
    }

    @Test
    fun getUsersReturnsErrorIfRetrievedError() = runTest {
        val errorCode = 404
        whenever(gitHubApi.fetchUsers()).thenReturn(Response.error(errorCode, "".toResponseBody()))
        val result = repository.getUsers()
        assertTrue((result as UserResult.Failure).message!!.contains(errorCode.toString()))
    }

    @Test
    fun getUsersReturnsErrorIfRetrievedFailure() = runTest {
        val failureMessage = "Failed to connect to server"
        whenever(gitHubApi.fetchUsers()).thenThrow(IllegalStateException(failureMessage))
        val result = repository.getUsers()
        assertEquals(failureMessage, (result as UserResult.Failure).message)
    }
}
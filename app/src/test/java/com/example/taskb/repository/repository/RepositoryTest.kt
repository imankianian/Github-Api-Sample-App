package com.example.taskb.repository.repository

import com.example.taskb.repository.remote.datasource.NetworkResult
import com.example.taskb.repository.remote.datasource.RemoteDataSource
import com.example.taskb.repository.remote.model.User
import com.github.javafaker.Faker
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class RepositoryTest {

    private val remoteDataSource: RemoteDataSource = mock()
    private val dispatcher = UnconfinedTestDispatcher()
    private val repository: Repository = RepositoryImpl(remoteDataSource, dispatcher)

    @Test
    fun whenGetUsersCallsRepositoryGetUsers() = runTest(dispatcher) {
        whenever(remoteDataSource.getUsers()).thenReturn(NetworkResult.Success(listOf()))
        repository.getUsers()
        verify(remoteDataSource).getUsers()
    }

    @Test
    fun getUsersReturnsListOfUsersIfRetrievedSuccessfully() = runTest(dispatcher) {
        val faker = Faker()
        val login = faker.name().username()
        val avatarUrl = faker.internet().avatar()
        val user = User(login, avatarUrl)
        whenever(remoteDataSource.getUsers()).thenReturn(NetworkResult.Success(listOf(user)))
        val result = repository.getUsers()
        assertEquals(user, (result as UserResult.Success).users[0])
    }

    @Test
    fun getUsersReturnsErrorIfRetrievedError() = runTest(dispatcher) {
        whenever(remoteDataSource.getUsers()).thenReturn(NetworkResult.Error(404, "Not found"))
        val result = repository.getUsers()
        assertEquals("404: Not found", (result as UserResult.Failure).message)
    }

    @Test
    fun getUsersReturnsErrorIfRetrievedFailure() = runTest(dispatcher) {
        val failureMessage = "Failed to connect to server"
        whenever(remoteDataSource.getUsers()).thenReturn(NetworkResult.Failure(failureMessage))
        val result = repository.getUsers()
        assertEquals(failureMessage, (result as UserResult.Failure).message)
    }
}
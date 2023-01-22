package com.example.taskb.repository.remote.datasource

import com.example.taskb.repository.remote.api.GitHubApi
import com.example.taskb.repository.remote.model.User
import com.github.javafaker.Faker
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

class RemoteDataSourceTest {

    private val gitHubApi: GitHubApi = mock()
    private val dispatcher = UnconfinedTestDispatcher()
    private val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl(gitHubApi, dispatcher)

    @Test
    fun whenGetUsersCallsFetchUsers() = runTest(dispatcher) {
        remoteDataSource.getUsers()
        verify(gitHubApi).fetchUsers()
    }

    @Test
    fun getUsersRetrievesUsersIfResponseWasSuccessful() = runTest(dispatcher) {
        val faker = Faker()
        val user = User(faker.name().username(), faker.internet().avatar())
        whenever(gitHubApi.fetchUsers()).thenReturn(Response.success(listOf(user)))
        val response = remoteDataSource.getUsers()
        assertEquals(user, (response as NetworkResult.Success).users[0])
    }

    @Test
    fun getUsersRetrievesErrorIfResponseWasNotSuccessful() = runTest(dispatcher) {
        val errorCode = 404
        whenever(gitHubApi.fetchUsers()).thenReturn(Response.error(errorCode,
            "".toResponseBody(null)))
        val response = remoteDataSource.getUsers()
        assertTrue((response as NetworkResult.Error).code == errorCode)
    }

    @Test
    fun getUsersRetrievesFailureIfNetworkRequestFailed() = runTest(dispatcher) {
        val failureMessage = "Failed to connect to server"
        whenever(gitHubApi.fetchUsers()).thenThrow(IllegalStateException(failureMessage))
        val response = remoteDataSource.getUsers()
        assertEquals(failureMessage, (response as NetworkResult.Failure).message)
    }
}
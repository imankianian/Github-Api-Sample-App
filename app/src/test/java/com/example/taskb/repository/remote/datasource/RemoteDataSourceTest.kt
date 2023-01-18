package com.example.taskb.repository.remote.datasource

import com.example.taskb.repository.remote.api.GitHubApi
import com.example.taskb.repository.remote.model.User
import com.github.javafaker.Faker
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class RemoteDataSourceTest {

    private val gitHubApi: GitHubApi = mock()
    private val faker = Faker()
    private val user = User(faker.name().username(), faker.internet().avatar())
    private val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl(gitHubApi)
    private val failureMessage = "Failed to connect to server"


    @Test
    fun getUsersRetrievesUsersIfResponseWasSuccessful() = runTest {
        whenever(gitHubApi.fetchUsers()).thenReturn(Response.success(listOf(user)))
        val response = remoteDataSource.getUsers()
        if (response is NetworkResult.Success) {
            assertEquals(user, response.users[0])
        }
    }

    @Test
    fun getUsersRetrievesErrorIfResponseWasNotSuccessful() = runTest {
        whenever(gitHubApi.fetchUsers()).thenReturn(Response.error(404, "".toResponseBody(null)))
        val response = remoteDataSource.getUsers()
        if (response is NetworkResult.Error) {
            assert(response.code == 404)
        }
    }

    @Test
    fun getUsersRetrievesFailureIfNetworkRequestFailed() = runTest {
        whenever(gitHubApi.fetchUsers()).thenThrow(IllegalStateException(failureMessage))
        val response = remoteDataSource.getUsers()
        if (response is NetworkResult.Failure) {
            assertEquals(failureMessage, response.message)
        }
    }
}
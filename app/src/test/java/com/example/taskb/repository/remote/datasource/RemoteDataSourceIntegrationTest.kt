package com.example.taskb.repository.remote.datasource

import com.example.taskb.repository.remote.api.GitHubApi
import com.example.taskb.repository.remote.model.User
import com.github.javafaker.Faker
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSourceIntegrationTest {

    private val mockWebServer = MockWebServer()

    private val gitHubApi by lazy {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApi::class.java)
    }

    private val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl(gitHubApi)
    private val faker = Faker()
    private val login = faker.name().username()
    private val avatarUrl = faker.internet().avatar()
    private val testJson = """[ { "login": "$login", "avatar_url": "$avatarUrl" } ]"""

    @Test
    fun getUsersRetrievesUsersIfResponseWasSuccessful() = runTest {
        mockWebServer.enqueue(MockResponse().setBody(testJson).setResponseCode(200))
        val result = remoteDataSource.getUsers()
        if ( result is NetworkResult.Success) {
            assertEquals(User(login, avatarUrl), result.users[0])
        }
    }

    @Test
    fun getUsersRetrievesErrorIfResponseWasNotSuccessful() = runTest {
        mockWebServer.enqueue(MockResponse().setBody("").setResponseCode(404))
        val result = remoteDataSource.getUsers()
        if (result is NetworkResult.Error) {
            assertTrue(result.code == 404)
        }
    }
}
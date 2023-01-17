package com.example.taskb.repository.remote.api

import com.example.taskb.repository.remote.model.User
import com.github.javafaker.Faker
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GitHubApiTest {

    private val mockWebServer = MockWebServer()
    private val faker = Faker()

    private val gitHubApi by lazy {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApi::class.java)
    }

    private val login = faker.name().username()
    private val avatarUrl = faker.internet().avatar()
    private val testJson = """[ { "login": "$login", "avatar_url": "$avatarUrl" } ]"""

    @Test
    fun fetchUsersEmitsUsers() = runTest {
        mockWebServer.enqueue(MockResponse().setBody(testJson).setResponseCode(200))
        val response = gitHubApi.fetchUsers()
        if (response.isSuccessful) {
            response?.body()?.let { users ->
                assertEquals(User(login, avatarUrl), users[0])
            }
        }
    }

    @Test
    fun fetchUsersCallsCorrectEndpoint() = runTest {
        mockWebServer.enqueue(MockResponse().setBody(testJson).setResponseCode(200))
        gitHubApi.fetchUsers()
        assertEquals("/users", mockWebServer.takeRequest().path)
    }
}
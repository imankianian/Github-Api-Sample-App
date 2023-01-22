package com.example.taskb.ui.viewmodel

import com.example.taskb.repository.remote.api.GitHubApi
import com.example.taskb.repository.remote.datasource.RemoteDataSource
import com.example.taskb.repository.remote.datasource.RemoteDataSourceImpl
import com.example.taskb.repository.remote.model.User
import com.example.taskb.repository.repository.Repository
import com.example.taskb.repository.repository.RepositoryImpl
import com.github.javafaker.Faker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class UsersViewModelIntegrationTest {

    private val dispatcher = UnconfinedTestDispatcher()
    private val gitHubApi: GitHubApi = mock()
    private val remoteDataSource: RemoteDataSource = RemoteDataSourceImpl(gitHubApi, dispatcher)
    private val repository: Repository = RepositoryImpl(remoteDataSource, dispatcher)
    private val usersViewModel = UsersViewModel(repository)

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getUsersUpdatesUsersUiStateWithUsersIfCallWasSuccessful() = runTest {
        val faker = Faker()
        val login = faker.name().username()
        val avatarUrl = faker.internet().avatar()
        val user = User(login, avatarUrl)
        whenever(gitHubApi.fetchUsers()).thenReturn(Response.success(listOf(user)))
        usersViewModel.getUsers()
        val result = usersViewModel.usersUiState.value
        assertEquals(user, (result as UsersUiState.Success).users[0])
    }

    @Test
    fun getUsersUpdatesUsersUiStateWithFailureIfCallWasNotSuccessful() = runTest {
        val errorCode = 404
        whenever(gitHubApi.fetchUsers()).thenReturn(Response.error(errorCode, "".toResponseBody()))
        usersViewModel.getUsers()
        val result = usersViewModel.usersUiState.value
        assertTrue((result as UsersUiState.Failure).message!!.contains(errorCode.toString()))
    }
}
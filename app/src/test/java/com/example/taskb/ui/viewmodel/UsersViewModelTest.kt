package com.example.taskb.ui.viewmodel

import com.example.taskb.repository.remote.model.User
import com.example.taskb.repository.repository.Repository
import com.example.taskb.repository.repository.UserResult
import com.github.javafaker.Faker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UsersViewModelTest {

    private val repository: Repository = mock()
    private val usersViewModel = UsersViewModel(repository)

    @Test
    fun usersUiStateIsInitializedWithLoadingState() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        val result = usersViewModel.usersUiState.value
        assertTrue(result == UsersUiState.Loading)
    }

    @Test
    fun getUsersCallsRepositoryGetUsers() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        usersViewModel.getUsers()
        verify(repository).getUsers()
    }

    @Test
    fun getUsersUpdatesUsersUiStateWithUsersIfCallWasSuccessful() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        val faker = Faker()
        val login = faker.name().username()
        val avatarUrl = faker.internet().avatar()
        val user = User(login, avatarUrl)
        whenever(repository.getUsers()).thenReturn(UserResult.Success(listOf(user)))
        usersViewModel.getUsers()
        val result = usersViewModel.usersUiState.value
        assertEquals(user, (result as UsersUiState.Success).users[0])
    }

    @Test
    fun getUsersUpdatesUsersUiStateWithFailureIfCallWasNotSuccessful() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        val errorMessage = "404: not found"
        whenever(repository.getUsers()).thenReturn(UserResult.Failure(errorMessage))
        usersViewModel.getUsers()
        val result = usersViewModel.usersUiState.value
        assertEquals(errorMessage, (result as UsersUiState.Failure).message)
    }
}
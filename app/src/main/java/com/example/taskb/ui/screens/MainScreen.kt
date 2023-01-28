package com.example.taskb.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.taskb.R
import com.example.taskb.repository.local.model.LocalUser
import com.example.taskb.ui.state.MainUiState
import com.example.taskb.ui.viewmodel.MainViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun MainScreen(mainViewModel: MainViewModel, onNavigateToDetails: (login: String) -> Unit) {
    when (val mainUiState = mainViewModel.mainUiState.collectAsStateWithLifecycle().value) {
        is MainUiState.Loading -> LoadingScreen {
            Image(painter = painterResource(id = R.drawable.ic_github_mark), contentDescription = "Github logo",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp))
        }
        is MainUiState.Success -> MainScreenContent(localUsers = mainUiState.localUsers, onNavigateToDetails)
        is MainUiState.Error -> ErrorScreen(message = mainUiState.message)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(localUsers: List<LocalUser>, onNavigateToDetails: (login: String) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.users),
                        fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                ),
            )
        }, content = {
            Column(modifier = Modifier.padding(it)) {
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(0.1f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp))
                UsersListScreen(localUsers = localUsers, onNavigateToDetails = onNavigateToDetails)
            }
        }
    )
}

@Composable
fun UsersListScreen(localUsers: List<LocalUser>, onNavigateToDetails: (login: String) -> Unit) {
    LazyColumn(modifier = columnModifier) {
        items(localUsers) { user ->
            UserCard(user = user, onNavigateToDetails)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCard(user: LocalUser, onNavigateToDetails: (login: String) -> Unit) {
    Card(onClick = { onNavigateToDetails(user.login) },
        modifier = cardModifier) {
        Row(modifier = userCardModifier,
            verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.Start) {
            UserAvatar(user = user)
            UserLoginName(user = user)
        }
    }
}

@Composable
fun UserAvatar(user: LocalUser) {
        AsyncImage(model = ImageRequest.Builder(LocalContext.current)
            .data(user.avatarUrl)
            .crossfade(true)
            .build(),
            contentDescription = user.login,
            contentScale = ContentScale.Fit,
            modifier = userAvatarModifier
        )
}

@Composable
fun UserLoginName(user: LocalUser) {
    Text(text = user.login,
        color = Color.Black,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        modifier = userLoginNameModifier
    )
}

private val columnModifier = Modifier.padding(10.dp).background(Color.White)
private val cardModifier = Modifier.padding(bottom = 20.dp)
private val userCardModifier = Modifier.height(40.dp).fillMaxWidth().background(Color.White)
private val userAvatarModifier = Modifier.width(40.dp).height(40.dp).clip(CircleShape)
private val userLoginNameModifier = Modifier.fillMaxSize().wrapContentHeight(CenterVertically)
    .padding(start = 10.dp)
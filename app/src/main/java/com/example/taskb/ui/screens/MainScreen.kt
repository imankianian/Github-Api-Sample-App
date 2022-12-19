package com.example.taskb.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.taskb.R
import com.example.taskb.repository.local.model.LocalUser
import com.example.taskb.ui.state.MainUiState
import com.example.taskb.ui.viewmodel.MainViewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel, onNavigateToDetails: (login: String) -> Unit) {
    when (val mainUiState = mainViewModel.mainUiState) {
        is MainUiState.Loading -> MainLoadingScreen()
        is MainUiState.Success -> UsersListScreen(localUsers = mainUiState.localUsers, onNavigateToDetails)
        is MainUiState.Error -> MainErrorScreen(message = mainUiState.message)
    }
}

@Composable
fun MainLoadingScreen() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        contentAlignment = Center) {
        Image(painter = painterResource(id = R.drawable.ic_github_mark), contentDescription = "Github logo",
        modifier = Modifier.width(100.dp).height(100.dp))
    }
}

@Composable
fun MainErrorScreen(message: String) {
    Text(text = message,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .wrapContentHeight(CenterVertically))
}

@Composable
fun UsersListScreen(localUsers: List<LocalUser>, onNavigateToDetails: (login: String) -> Unit) {
    LazyColumn(modifier = Modifier
        .padding(10.dp)
        .background(Color.White)) {
        items(localUsers) { user ->
            UserCard(user = user, onNavigateToDetails)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCard(user: LocalUser, onNavigateToDetails: (login: String) -> Unit) {
    Card(onClick = { onNavigateToDetails(user.login) },
        modifier = Modifier.padding(bottom = 20.dp)) {
        Row(modifier = Modifier
            .height(40.dp)
            .fillMaxWidth()
            .background(Color.White),
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
            modifier = Modifier.width(40.dp).height(40.dp).clip(CircleShape)
        )
}

@Composable
fun UserLoginName(user: LocalUser) {
    Text(text = user.login,
        color = Color.Black,
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .wrapContentHeight(CenterVertically)
            .padding(start = 10.dp)
    )
}
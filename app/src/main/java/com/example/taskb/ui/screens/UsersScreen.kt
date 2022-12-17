package com.example.taskb.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.taskb.data.model.User
import com.example.taskb.ui.UsersUiState
import com.example.taskb.ui.theme.BrilliantAzure
import com.example.taskb.ui.viewmodel.UsersViewModel

@Composable
fun UsersScreen(viewModel: UsersViewModel) {
    when (val usersUiState = viewModel.usersUiState) {
        is UsersUiState.Loading -> UsersLoadingScreen()
        is UsersUiState.Success -> UsersListScreen(users = usersUiState.users)
        is UsersUiState.Error -> UsersErrorScreen(message = usersUiState.message)
    }
}

@Composable
fun UsersLoadingScreen() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        contentAlignment = Center) {
        CircularProgressIndicator(modifier = Modifier
            .wrapContentHeight(CenterVertically))
    }
}

@Composable
fun UsersErrorScreen(message: String) {
    Text(text = message,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .wrapContentHeight(CenterVertically))
}

@Composable
fun UsersListScreen(users: List<User>) {
    LazyColumn(modifier = Modifier
        .padding(10.dp)
        .background(Color.White)) {
        items(users) { user ->
            UserCard(user = user)
        }
    }
}

@Composable
fun UserCard(user: User) {
    Card(modifier = Modifier.padding(bottom = 20.dp)) {
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
fun UserAvatar(user: User) {
        AsyncImage(model = ImageRequest.Builder(LocalContext.current)
            .data(user.avatarUrl)
            .crossfade(true)
            .build(),
            contentDescription = user.loginName,
            contentScale = ContentScale.Fit,
            modifier = Modifier.width(40.dp).height(40.dp).clip(CircleShape)
        )
}

@Composable
fun UserLoginName(user: User) {
    Text(text = user.loginName,
        color = BrilliantAzure,
        fontSize = 18.sp,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .wrapContentHeight(CenterVertically)
            .padding(start = 10.dp)
    )
}
package com.example.taskb.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskb.R
import com.example.taskb.repository.remote.model.Repo
import com.example.taskb.ui.state.UserDetailsUiState
import com.example.taskb.ui.viewmodel.UserDetailsViewModel

@Composable
fun UserDetailsScreen(userDetailsViewModel: UserDetailsViewModel, onNavigateToDetails: (login: String) -> Unit) {
    when (val userDetailsUiState = userDetailsViewModel.userDetailsUiState) {
        is UserDetailsUiState.Loading -> ReposLoadingScreen()
        is UserDetailsUiState.Success -> ReposListScreen(repos = userDetailsUiState.repos, onNavigateToDetails)
        is UserDetailsUiState.Error -> ReposErrorScreen(message = userDetailsUiState.message)
    }
}

@Composable
fun ReposLoadingScreen() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier
            .wrapContentHeight(Alignment.CenterVertically))
    }
}

@Composable
fun ReposErrorScreen(message: String) {
    Text(text = message,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .wrapContentHeight(Alignment.CenterVertically))
}

@Composable
fun ReposListScreen(repos: List<Repo>, onNavigateToDetails: (login: String) -> Unit) {
    LazyColumn(modifier = Modifier.background(Color.White)) {
        items(repos) { repo ->
            Spacer(modifier = Modifier.size(10.dp))
            RepoCard(repo = repo, onNavigateToDetails)
            Spacer(modifier = Modifier.size(10.dp))
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(0.05f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoCard(repo: Repo, onNavigateToDetails: (login: String) -> Unit) {
    Card(onClick = { onNavigateToDetails(repo.htmlUrl) }) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)) {
            Spacer(modifier = Modifier.size(20.dp))
            DisplayRepo(repo = repo)
        }
    }
}

@Composable
fun DisplayRepo(repo: Repo) {
    Column() {
        RepoName(name = repo.name)
        RepoUpdateDate(date = repo.lastUpdate)
        Spacer(modifier = Modifier.size(5.dp))
        Row() {
            RepoStar(count = repo.stars)
            Spacer(modifier = Modifier.size(15.dp))
            RepoLanguage(language = repo.language ?: "Unknown")
        }
    }
}

@Composable
fun RepoName(name: String) {
    Text(text = name,
        color = Color.Black,
        fontSize = 17.sp,
        textAlign = TextAlign.Start
    )
}

@Composable
fun RepoUpdateDate(date: String) {
    Text(text = date,
        color = Color.LightGray,
        fontSize = 10.sp,
        textAlign = TextAlign.Start
    )
}

@Composable
fun RepoStar(count: Int) {
    Row() {
        Image(painter = painterResource(id = R.drawable.ic_star),
            contentDescription = "stars",
            modifier = Modifier.width(15.dp).height(20.dp)
            )
        Spacer(modifier = Modifier.size(5.dp))
        Text(text = count.toString(),
            color = Color.Gray,
            fontSize = 15.sp,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun RepoLanguage(language: String) {
    Row() {
        Image(painterResource(id = R.drawable.ic_language),
            contentDescription = "programming language",
            modifier = Modifier.width(15.dp).height(20.dp)
        )
    }
    Spacer(modifier = Modifier.size(5.dp))
    Text(text = language,
        color = Color.Gray,
        fontSize = 15.sp,
        textAlign = TextAlign.Start
    )
}
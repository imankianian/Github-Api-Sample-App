package com.example.taskb.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskb.data.model.Repo
import com.example.taskb.ui.ReposUiState
import com.example.taskb.ui.theme.BrilliantAzure
import com.example.taskb.ui.viewmodel.UserReposViewModel

@Composable
fun UserReposScreen(viewModel: UserReposViewModel, onNavigateToDetails: (login: String) -> Unit) {
    when (val reposUiState = viewModel.reposUiState) {
        is ReposUiState.Loading -> UserReposLoadingScreen()
        is ReposUiState.Success -> UserReposListScreen(repos = reposUiState.repos, onNavigateToDetails)
        is ReposUiState.Error -> UserReposErrorScreen(message = reposUiState.message)
    }
}

@Composable
fun UserReposLoadingScreen() {
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
fun UserReposErrorScreen(message: String) {
    Text(text = message,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .wrapContentHeight(Alignment.CenterVertically))
}

@Composable
fun UserReposListScreen(repos: List<Repo>, onNavigateToDetails: (login: String) -> Unit) {
    LazyColumn(modifier = Modifier
        .padding(10.dp)
        .background(Color.White)) {
        items(repos) { repo ->
            RepoCard(repo = repo, onNavigateToDetails)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoCard(repo: Repo, onNavigateToDetails: (login: String) -> Unit) {
    Card(onClick = { onNavigateToDetails(repo.htmlUrl) },
        modifier = Modifier.padding(bottom = 20.dp)) {
        Column(modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .background(Color.White)) {
            RepoName(name = repo.name)
            RepoUpdateDate(date = repo.lastUpdate)
            RepoStar(count = repo.stars)
            RepoLanguage(language = repo.language ?: "Unknown")
        }
    }
}

@Composable
fun RepoName(name: String) {
    Text(text = name,
        color = BrilliantAzure,
        fontSize = 18.sp,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(start = 10.dp)
    )
}

@Composable
fun RepoUpdateDate(date: String) {
    Text(text = date,
        color = BrilliantAzure,
        fontSize = 18.sp,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(start = 10.dp)
    )
}

@Composable
fun RepoStar(count: Int) {
    Text(text = count.toString(),
        color = BrilliantAzure,
        fontSize = 18.sp,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(start = 10.dp)
    )
}

@Composable
fun RepoLanguage(language: String) {
    Text(text = language,
        color = BrilliantAzure,
        fontSize = 18.sp,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(start = 10.dp)
    )
}
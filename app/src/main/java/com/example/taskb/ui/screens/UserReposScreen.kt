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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskb.R
import com.example.taskb.data.model.Repo
import com.example.taskb.ui.ReposUiState
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
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)) {
            Row() {
                Spacer(modifier = Modifier.size(20.dp))
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
            modifier = Modifier
                .width(15.dp)
                .height(20.dp),
            contentDescription = "stars")
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
        modifier = Modifier.width(15.dp).height(20.dp),
        contentDescription = "programming language")
    }
    Spacer(modifier = Modifier.size(5.dp))
    Text(text = language,
        color = Color.Gray,
        fontSize = 15.sp,
        textAlign = TextAlign.Start
    )
}
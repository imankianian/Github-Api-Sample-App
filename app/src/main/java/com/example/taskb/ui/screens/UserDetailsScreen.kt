package com.example.taskb.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskb.R
import com.example.taskb.repository.local.model.LocalRepo
import com.example.taskb.ui.state.UserDetailsUiState
import com.example.taskb.ui.viewmodel.UserDetailsViewModel

@Composable
fun UserDetailsScreen(userDetailsViewModel: UserDetailsViewModel,
                      onNavigateToDetails: (login: String) -> Unit,
                        onBackPressed: () -> Unit) {
    when (val userDetailsUiState = userDetailsViewModel.userDetailsUiState) {
        is UserDetailsUiState.Loading -> LoadingScreen {
            CircularProgressIndicator(
                modifier = Modifier
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        }
        is UserDetailsUiState.Success -> UserDetailsContent(userDetailsViewModel.login ?: "",
            localRepos = userDetailsUiState.localRepos, onNavigateToDetails, onBackPressed)
        is UserDetailsUiState.Error -> ErrorScreen(message = userDetailsUiState.message)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsContent(login: String, localRepos: List<LocalRepo>,
                       onNavigateToDetails: (login: String) -> Unit,
                       onBackPressed: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column() {
                        Text(text = login,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Gray
                        )
                        Text(text = stringResource(id = R.string.repositories),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
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
                ReposListScreen(localRepos = localRepos, onNavigateToDetails)
            }
        }
    )
}

@Composable
fun ReposListScreen(localRepos: List<LocalRepo>, onNavigateToDetails: (login: String) -> Unit) {
    LazyColumn(modifier = Modifier.background(Color.White)) {
        items(localRepos) { repo ->
            Spacer(modifier = Modifier.size(15.dp))
            RepoCard(localRepo = repo, onNavigateToDetails)
            Spacer(modifier = Modifier.size(15.dp))
            Divider(color = MaterialTheme.colorScheme.onSurface.copy(0.1f),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoCard(localRepo: LocalRepo , onNavigateToDetails: (login: String) -> Unit ) {
    Card(onClick = { onNavigateToDetails(localRepo.htmlUrl) }) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)) {
            Spacer(modifier = Modifier.size(20.dp))
            DisplayRepo(localRepo = localRepo)
        }
    }
}

@Composable
fun DisplayRepo(localRepo: LocalRepo) {
    Column() {
        RepoName(name = localRepo.name)
        RepoUpdateDate(date = localRepo.lastUpdate)
        Spacer(modifier = Modifier.size(5.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            RepoStar(count = localRepo.stars)
            Spacer(modifier = Modifier.size(15.dp))
            RepoLanguage(language = localRepo.language ?: "Unknown")
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
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = R.drawable.ic_star),
            contentDescription = "stars",
        modifier = Modifier.height(20.dp))
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
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(painterResource(id = R.drawable.ic_language),
            contentDescription = "programming language",
        modifier = Modifier.height(20.dp))

    }
    Spacer(modifier = Modifier.size(5.dp))
    Text(text = language,
        color = Color.Gray,
        fontSize = 15.sp,
        textAlign = TextAlign.Start
    )
}
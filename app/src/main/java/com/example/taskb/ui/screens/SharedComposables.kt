package com.example.taskb.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskb.R

@Composable
fun LoadingScreen(composable: @Composable () -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        composable()
    }
}

@Composable
fun ErrorScreen(message: String) {
    Column(modifier = Modifier
        .width(300.dp)
        .fillMaxHeight()
        .wrapContentHeight(CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.drawable.ic_offline),
            contentDescription = "You are offline",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .wrapContentHeight(CenterVertically),
                alpha = 0.3f)
        Divider(color = Color.White,
            modifier = Modifier.height(20.dp))
        Text(text = message,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 10.sp)
    }
}
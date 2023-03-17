package com.ircarren.ghkanban.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ircarren.ghkanban.ui.issue.MainScreenIssues

@Composable
fun IssuesScreen(modifier: Modifier = Modifier, navController: NavController, repoName:String, userName:String) {
    MainScreenIssues(navController = navController, repoName = repoName, userName = userName)
    println("IssuesScreen: $repoName")
}
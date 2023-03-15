package com.ircarren.ghkanban.screens

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun IssuesScreen(modifier: Modifier = Modifier, navController: NavController, repoName: String, application: Application){
    mainContainer(application = application , navController = navController)
}
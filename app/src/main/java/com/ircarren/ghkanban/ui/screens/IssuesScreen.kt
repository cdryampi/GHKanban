package com.ircarren.ghkanban.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ircarren.ghkanban.ui.issue.MainScreenIssues
import com.ircarren.ghkanban.ui.viewModel.IssueViewModel

@Composable
fun IssuesScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: IssueViewModel
) {
    MainScreenIssues(navController = navController, viewModel = viewModel)
    viewModel.callAPI()
}
package com.ircarren.ghkanban.ui.issue

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ircarren.ghkanban.ui.issue.tab.IssuesTab
import com.ircarren.ghkanban.ui.viewModel.IssueViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreenIssues(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: IssueViewModel
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        IssuesTab(navController = navController, issuesViewModel = viewModel)
    }
}

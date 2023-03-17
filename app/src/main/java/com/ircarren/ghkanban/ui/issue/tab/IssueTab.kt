package com.ircarren.ghkanban.ui.issue.tab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.ircarren.ghkanban.ui.issue.tab.content.issuesTabsContent
import com.ircarren.ghkanban.ui.issue.tab.tabContainer.IssuesTabs
import com.ircarren.ghkanban.ui.viewModel.IssueViewModel

@OptIn(ExperimentalPagerApi::class)
@ExperimentalPagerApi
@Composable
fun IssuesTab(
    modifier: Modifier = Modifier,
    navController: NavController,
    issuesViewModel: IssueViewModel
) {
    val pagerState = rememberPagerState(pageCount = 4)
    Column(modifier = Modifier.background(Color.White)) {
        TopAppBar(backgroundColor = Color.DarkGray) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TitleIssues(navController = navController)
            }

        }
        IssuesTabs(pagerState = pagerState)
        issuesTabsContent(
            pagerState = pagerState,
            navController = navController,
            issuesViewModel = issuesViewModel,
            modifier = Modifier.padding(10.dp)
        )

    }
}

@Composable
fun TitleIssues(modifier: Modifier = Modifier, navController: NavController) {
    TopAppBar(
        title = { Text(text = "GH Kanban", color = Color.White, style = MaterialTheme.typography.titleLarge) },
        navigationIcon = {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )
        },
        backgroundColor = Color.DarkGray,
    )
}
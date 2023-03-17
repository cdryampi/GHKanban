package com.ircarren.ghkanban.ui.issue.tab.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.ircarren.ghkanban.models.Issue
import com.ircarren.ghkanban.ui.issue.tab.screen.IssuesTabContentScreenList
import com.ircarren.ghkanban.ui.viewModel.IssueViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun issuesTabsContent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    navController: NavController,
    issuesViewModel: IssueViewModel
) {


    val backlog: List<Issue> by issuesViewModel.issueBacklogLocal.observeAsState(listOf())
    val next: List<Issue> by issuesViewModel.issueNextLocal.observeAsState(listOf())
    val progress: List<Issue> by issuesViewModel.issueInProgressLocal.observeAsState(listOf())
    val done: List<Issue> by issuesViewModel.issueDoneLocal.observeAsState(listOf())


    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> IssuesTabContentScreenList(data = backlog, viewModel = issuesViewModel)
            1 -> IssuesTabContentScreenList(data = next, viewModel = issuesViewModel)
            2 -> IssuesTabContentScreenList(data = progress, viewModel = issuesViewModel)
            3 -> IssuesTabContentScreenList(data = done, viewModel = issuesViewModel)
        }
    }

}

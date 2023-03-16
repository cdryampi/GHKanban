package com.ircarren.ghkanban.screens

import android.app.Application
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.*
import com.ircarren.ghkanban.R
import com.ircarren.ghkanban.enums.IssueStatus
import com.ircarren.ghkanban.models.Issue
import com.ircarren.ghkanban.models.Repository
import com.ircarren.ghkanban.viewModel.IssueViewModel
import com.ircarren.ghkanban.viewModel.RepoLocalViewModel
import kotlinx.coroutines.launch

@Composable
fun IssuesScreen(modifier: Modifier = Modifier, navController: NavController, repoName:String, userName:String) {
    mainScreenIssues(navController = navController, repoName = repoName, userName = userName)
    println("IssuesScreen: $repoName")
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun mainScreenIssues(modifier: Modifier = Modifier, navController: NavController,repoName:String, userName:String) {
    val issuesViewModel = IssueViewModel(repoName = repoName, username = userName)
    Column(modifier = Modifier.fillMaxWidth()) {
        issuesTab(navController = navController, issuesViewModel = issuesViewModel)
    }
}


@OptIn(ExperimentalPagerApi::class)
@ExperimentalPagerApi
@Composable
fun issuesTab(
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
        issuesTabs(pagerState = pagerState)
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
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        navController.popBackStack()
                    }

            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "GH Kanban", style = MaterialTheme.typography.titleLarge, color = Color.White)
            // image from static resources

        }

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun issuesTabs(pagerState: PagerState) {
    val list = listOf(
        "BACKLOG" to Icons.Default.List,
        "NEXT" to Icons.Default.Home,
        "PROGRESS" to Icons.Default.List,
        "DONE" to Icons.Default.Home
    )
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        },
        backgroundColor = Color.DarkGray
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                icon = {
                    Icon(imageVector = list[index].second, contentDescription = null)
                },
                text = {
                    Text(
                        list[index].first,
                        color = if (pagerState.currentPage == index) Color.White else Color.LightGray
                    )
                },

                selected = pagerState.currentPage == index,
                onClick = {
                    // on below line we are specifying the scope.
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

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
            0 -> issuesTabContentScreenList(data = backlog, viewModel = issuesViewModel)
            1 -> issuesTabContentScreenList(data = next, viewModel = issuesViewModel)
            2 -> issuesTabContentScreenList(data = progress, viewModel = issuesViewModel)
            3 -> issuesTabContentScreenList(data = done, viewModel = issuesViewModel)
        }
    }
}


@Composable
fun issuesTabContentScreenList(data: List<Issue>, viewModel: IssueViewModel) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(data.size) { index ->
            cardIssue(issue = data[index], viewModel = viewModel)
            GenericSpacer()
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun cardIssue(modifier: Modifier = Modifier, issue: Issue, viewModel: IssueViewModel) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        onClick = {
        }
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                issue.title?.let { Text(text = it, style = MaterialTheme.typography.titleLarge) }
                Spacer(modifier = Modifier.height(16.dp))
                issue.body?.let { Text(text = it, style = MaterialTheme.typography.bodyLarge) }
                issue.status?.let {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {

                    when (issue.status?.name) {

                        IssueStatus.BACKLOG.name -> {
                            Icon(imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                tint = Color.DarkGray,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        viewModel.changeToNext(issue)
                                        //println(issue)
                                        println("BACKLOG ${viewModel.issueNextLocal.value}")
                                    }

                            )
                        }

                        IssueStatus.NEXT.name -> {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {
                                Icon(imageVector = Icons.Default.ArrowForward,
                                    contentDescription = null,
                                    tint = Color.DarkGray,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clickable {
                                            viewModel.changeToInProgress(issue)
                                        })
                                Spacer(modifier = Modifier.height(16.dp))
                                Icon(imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null,
                                    tint = Color.DarkGray,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clickable {
                                            viewModel.changetoBacklog(issue)
                                        })
                            }

                        }

                        IssueStatus.IN_PROGRESS.name -> {
                            Column() {
                                Icon(imageVector = Icons.Default.ArrowForward,
                                    contentDescription = null,
                                    tint = Color.DarkGray,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clickable {
                                            viewModel.changeToDone(issue)
                                        })
                                Spacer(modifier = Modifier.height(16.dp))
                                Icon(imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null,
                                    tint = Color.DarkGray,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clickable {
                                            viewModel.changeToNextInProgress(issue)
                                        })
                            }


                        }

                        IssueStatus.DONE.name -> {
                            Icon(imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.DarkGray,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        viewModel.changeToDoneInProgress(issue)
                                    })
                        }

                }
            }
        }

    }

}
package com.ircarren.ghkanban.ui.repository.tab.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.ircarren.ghkanban.ui.repository.tab.screen.TabContentScreenList
import com.ircarren.ghkanban.ui.repository.tab.screen.TabContentScreenListLocal
import com.ircarren.ghkanban.ui.viewModel.RepoLocalViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(modifier: Modifier = Modifier, pagerState: PagerState, navController: NavController) {

    val username = "cdryampi"
    val viewModel = viewModel<RepoLocalViewModel>()
    val myList: List<String> by viewModel.repoIdsLocal.observeAsState(listOf())

    val repos by viewModel.repos.observeAsState(emptyList())


    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> TabContentScreenList(data = repos, true, viewModel, navController = navController)
            1 -> TabContentScreenListLocal(data = myList.toList(), false,viewModel, navController = navController)

        }

    }
}
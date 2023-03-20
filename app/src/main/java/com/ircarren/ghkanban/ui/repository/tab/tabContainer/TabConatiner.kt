package com.ircarren.ghkanban.ui.repository.tab.tabContainer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.ircarren.ghkanban.ui.repository.Title
import com.ircarren.ghkanban.ui.repository.GenericTabs
import com.ircarren.ghkanban.ui.repository.tab.content.TabsContent
import com.ircarren.ghkanban.ui.viewModel.RepoLocalViewModel

@OptIn(ExperimentalPagerApi::class)
@ExperimentalPagerApi
@Composable
fun GenericTab(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: RepoLocalViewModel
) {
    val pagerState = rememberPagerState(pageCount = 2)
    Column(modifier = Modifier.background(Color.White)) {
        TopAppBar(backgroundColor = Color.DarkGray) {
            Column(
                modifier = Modifier.fillMaxWidth().height(250.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                Title()
            }
        }
        GenericTabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState, navController = navController, viewModel = viewModel)
    }
}
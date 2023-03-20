package com.ircarren.ghkanban.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ircarren.ghkanban.ui.repository.MainContainer
import com.ircarren.ghkanban.ui.viewModel.RepoLocalViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ReposScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: RepoLocalViewModel
){
    MainContainer(navController = navController, viewModel = viewModel)
}
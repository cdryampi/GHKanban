package com.ircarren.ghkanban.screens

import android.app.Application
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ircarren.ghkanban.MainActivity

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ReposScreen(modifier: Modifier = Modifier, navController: NavController){

    mainContainer(navController = navController)
}
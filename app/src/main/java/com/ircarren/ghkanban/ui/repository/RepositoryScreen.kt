package com.ircarren.ghkanban.ui.repository

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ircarren.ghkanban.R
import com.ircarren.ghkanban.ui.repository.tab.tabContainer.GenericTab
import com.ircarren.ghkanban.ui.viewModel.RepoLocalViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainContainer(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: RepoLocalViewModel
) {
    Column(modifier = Modifier.fillMaxSize()) {
        GenericTab(navController = navController, viewModel = viewModel)
    }
}




@Composable
fun Title(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center){
            Text(text = "GH Kanban", style = MaterialTheme.typography.titleLarge, color = Color.White)
            // image from static resources
            Box(modifier = Modifier.fillMaxSize().background(Color.DarkGray).height(80.dp), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(55.dp)
                )
            }

        }

    }
}

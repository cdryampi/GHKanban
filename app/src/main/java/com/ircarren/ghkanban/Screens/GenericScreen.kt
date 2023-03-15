package com.ircarren.ghkanban.Screens

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import com.ircarren.ghkanban.ViewModel.RepoLocal
import com.ircarren.ghkanban.models.Repo
import com.ircarren.ghkanban.providers.RepoProvider
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun mainContainer(modifier: Modifier = Modifier, application: Application) {
    Column(modifier = Modifier.fillMaxWidth()) {
        GenericTab(application = application)
    }
}


@Composable
fun Title(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(text = "GH Kanban", style = MaterialTheme.typography.titleLarge, color = Color.White)
    }
}


@OptIn(ExperimentalPagerApi::class)
@ExperimentalPagerApi
@Composable
fun GenericTab(modifier: Modifier = Modifier, application: Application) {
    val pagerState = rememberPagerState(pageCount = 2)
    Column(modifier = Modifier.background(Color.White)) {
        TopAppBar(backgroundColor = Color.DarkGray) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Title()
            }

        }
        Tabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState, application = application)

    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf("Explore" to Icons.Default.List, "Local" to Icons.Default.Home)
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
fun TabsContent(modifier: Modifier = Modifier, pagerState: PagerState, application: Application) {
    val viewModel: RepoLocal = RepoLocal(application)
    val myList: List<String> by viewModel.repoIdsLocal.observeAsState(listOf())
    viewModel.loadRepoLocal()
    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> TabContentScreenList(data = RepoProvider.repos, true, viewModel)
            1 -> TabContentScreenListLocal(data = myList.toList(), false,viewModel)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabContentScreenListLocal(data: List<String>, b: Boolean, viewModel: RepoLocal) {
    genericSpacer()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        data.forEach {
            Card(modifier = Modifier.fillMaxWidth().height(100.dp), shape = MaterialTheme.shapes.medium, onClick = {}){
                Row() {

                    Text(text = it)
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = Color.DarkGray, modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxSize()
                        .clickable {
                            viewModel.deleteOneRefRepoLocal(it)
                        })
                }
            }


            genericSpacer()
        }
    }
}


@Composable
fun TabContentScreenList(data: List<Repo>, isFavorite: Boolean, viewModel: RepoLocal) {
    genericSpacer()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        data.forEach {
            CardRepo(it, isFavorite, viewModel)
            genericSpacer()
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardRepo(repo: Repo, isFavorite: Boolean, viewModel: RepoLocal){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        onClick = {}
    ) {
        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){

            Column(modifier = Modifier.padding(16.dp)) {
                repo.name?.let { Text(text = it, style = MaterialTheme.typography.titleLarge) }
                Spacer(modifier = Modifier.height(16.dp))
                repo.description?.let { Text(text = it, style = MaterialTheme.typography.bodyLarge) }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Box(modifier = Modifier.size(64.dp)){
                    if (isFavorite){
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.DarkGray, modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                            .clickable {
                                viewModel.saveRepoLocal(repo)
                            })
                    }else{
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = Color.DarkGray, modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                            .clickable {
                                //viewModel.deleteOneRefRepoLocal(repo)
                            })
                    }
                }
            }
        }

    }
}

@Composable
fun genericSpacer() {
    Spacer(modifier = Modifier.height(16.dp))
}


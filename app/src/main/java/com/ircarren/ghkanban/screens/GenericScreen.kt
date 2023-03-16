package com.ircarren.ghkanban.screens

import android.app.Application
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.*
import com.ircarren.ghkanban.R
import com.ircarren.ghkanban.viewModel.RepoLocalViewModel
import com.ircarren.ghkanban.models.Repository
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun mainContainer(modifier: Modifier = Modifier, navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        GenericTab(navController = navController)
    }
}




@Composable
fun Title(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
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


@OptIn(ExperimentalPagerApi::class)
@ExperimentalPagerApi
@Composable
fun GenericTab(modifier: Modifier = Modifier, navController: NavController) {
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
        genericTabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState, navController = navController)

    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun genericTabs(pagerState: PagerState) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabContentScreenListLocal(data: List<String>, b: Boolean, viewModel: RepoLocalViewModel, navController: NavController) {
    GenericSpacer()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        data.forEach {


            val localRepo = Repository(it)
            CardRepo(localRepo, b, viewModel, navController)
            GenericSpacer()
        }
    }
}


@Composable
fun TabContentScreenList(data: List<Repository>, isFavorite: Boolean, viewModel: RepoLocalViewModel, navController: NavController) {
    GenericSpacer()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        data.forEach {
            CardRepo(it, isFavorite, viewModel, navController)
            GenericSpacer()
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardRepo(repo: Repository, isFavorite: Boolean, viewModel: RepoLocalViewModel, navController: NavController){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        onClick = {
            navController.navigate("issues/${"cdryampi"}/${repo.name}")
            //navController.navigate("issues")
        }
    ) {
        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){

            Column(modifier = Modifier
                .padding(16.dp)
                .weight(1f)) {
                repo.name?.let { Text(text = it, style = MaterialTheme.typography.titleLarge) }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "yampi", style = MaterialTheme.typography.bodyLarge)
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Box(modifier = Modifier.size(64.dp)){
                    if (isFavorite){
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.DarkGray, modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                            .clickable {
                                viewModel.saveRepoLocal(repo.name)
                            })
                    }else{
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null, tint = Color.DarkGray, modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                            .clickable {
                                viewModel.deleteOneRefRepoLocal(repo.name)
                                println("delete")
                            })
                    }
                }
            }
        }

    }
}

@Composable
fun GenericSpacer() {
    Spacer(modifier = Modifier.height(16.dp))
}


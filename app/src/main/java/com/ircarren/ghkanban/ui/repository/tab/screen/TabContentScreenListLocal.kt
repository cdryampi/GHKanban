package com.ircarren.ghkanban.ui.repository.tab.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ircarren.ghkanban.models.Repository
import com.ircarren.ghkanban.ui.repository.card.CardRepo
import com.ircarren.ghkanban.ui.screens.GenericSpacer
import com.ircarren.ghkanban.ui.viewModel.RepoLocalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabContentScreenListLocal(data: List<String>, b: Boolean, viewModel: RepoLocalViewModel, navController: NavController) {
    GenericSpacer()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(data.size) { index ->
            CardRepo(repo = Repository(data[index]), navController = navController, onClick = {
                viewModel.deleteListReposToPreferences(Repository(data[index]))
            }, isFavorite = b)
            GenericSpacer()
        }
        /*
        data.forEach {


            val localRepo = Repository(it)
            CardRepo(repo = localRepo, navController = navController, onClick = {
                viewModel.deleteListReposToPreferences(localRepo)
            }, isFavorite = b)
            GenericSpacer()
        }*/
    }
}
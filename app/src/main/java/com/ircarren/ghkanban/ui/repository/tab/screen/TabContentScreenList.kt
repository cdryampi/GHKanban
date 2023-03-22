package com.ircarren.ghkanban.ui.repository.tab.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ircarren.ghkanban.models.Repository
import com.ircarren.ghkanban.ui.repository.card.CardRepo
import com.ircarren.ghkanban.ui.screens.GenericSpacer
import com.ircarren.ghkanban.ui.viewModel.RepoLocalViewModel

@Composable
fun TabContentScreenList(
    data: List<Repository> ?= List<Repository>(0) {
        Repository("sample","sample.html")
    }
    ,
    isFavorite: Boolean,
    viewModel: RepoLocalViewModel,
    navController: NavController
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (data != null) {
            items(data.size) { index ->
                if (data[index] != null) {
                    CardRepo(repo = data?.get(index) ?: Repository("sample","sample.html"), navController = navController, onClick = {
                        if (isFavorite) {

                            viewModel.addRepo(data[index])

                            viewModel.addRepo(data[index])
                        } else {
                            //viewModel.addRepo(it)
                        }
                    }, isFavorite = isFavorite)
                    Spacer(modifier = Modifier.padding(16.dp))
                }

            }
        }

    }
}
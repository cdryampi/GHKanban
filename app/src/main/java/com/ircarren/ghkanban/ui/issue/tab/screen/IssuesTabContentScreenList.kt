package com.ircarren.ghkanban.ui.issue.tab.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ircarren.ghkanban.models.Issue
import com.ircarren.ghkanban.ui.issue.card.CardIssue
import com.ircarren.ghkanban.ui.screens.GenericSpacer

import com.ircarren.ghkanban.ui.viewModel.IssueViewModel

@Composable
fun IssuesTabContentScreenList(data: List<Issue>, viewModel: IssueViewModel) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(data.size) { index ->
            CardIssue(issue = data[index], viewModel = viewModel)
            GenericSpacer()
        }
    }
}
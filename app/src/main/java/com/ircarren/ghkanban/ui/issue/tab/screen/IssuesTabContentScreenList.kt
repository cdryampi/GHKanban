package com.ircarren.ghkanban.ui.issue.tab.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ircarren.ghkanban.data.enums.IssueStatus
import com.ircarren.ghkanban.models.Issue
import com.ircarren.ghkanban.ui.issue.card.CardIssue
import com.ircarren.ghkanban.ui.screens.GenericSpacer

import com.ircarren.ghkanban.ui.viewModel.IssueViewModel


// ScreenList(items: List<Issue>, showNext: Boolean, showPrev: Boolean, onNextStateRequest: (Issue) -> Unit, onPrevStateRequest: (Issue) -> Unit)
@Composable
fun IssuesTabContentScreenList(
    data: List<Issue>,
    viewModel: IssueViewModel
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(data.size) { index ->

            val issue = data[index]
            var showNext = true
            var showPrev = true

            when(issue.status?.name){
                IssueStatus.BACKLOG.name ->{
                    showNext = true
                    showPrev = false
                }

                IssueStatus.NEXT.name ->{
                    showNext = true
                    showPrev = true
                }

                IssueStatus.IN_PROGRESS.name ->{
                    showNext = true
                    showPrev = true
                }

                IssueStatus.DONE.name ->{
                    showNext = false
                    showPrev = true
                }

            }

            CardIssue(
                issue = data[index],
                showNext = showNext,
                showPrev = showPrev,
                onNextStateRequest = { viewModel.changeToNext(it) },
                onPrevStateRequest = { viewModel.changeToPrev(it) }
            )


            GenericSpacer()
        }
    }
}
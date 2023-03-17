package com.ircarren.ghkanban.ui.issue.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ircarren.ghkanban.data.enums.IssueStatus
import com.ircarren.ghkanban.models.Issue
import com.ircarren.ghkanban.ui.viewModel.IssueViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardIssue(modifier: Modifier = Modifier, issue: Issue, viewModel: IssueViewModel, showNext:Boolean, showPrev:Boolean, onNextStateRequest: (Issue) -> Unit, onPrevStateRequest: (Issue) -> Unit) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        onClick = {
        }
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                issue.title?.let { Text(text = it, style = MaterialTheme.typography.titleLarge) }
                Spacer(modifier = Modifier.height(16.dp))
                issue.body?.let { Text(text = it, style = MaterialTheme.typography.bodyLarge) }
                issue.status?.let {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {

                when (issue.status?.name) {

                    IssueStatus.BACKLOG.name -> {
                        Icon(imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color.DarkGray,
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    onNextStateRequest(issue)
                                }

                        )
                    }

                    IssueStatus.NEXT.name -> {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Icon(imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                tint = Color.DarkGray,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        viewModel.changeToInProgress(issue)
                                    })
                            Spacer(modifier = Modifier.height(16.dp))
                            Icon(imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.DarkGray,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        viewModel.changetoBacklog(issue)
                                    })
                        }

                    }

                    IssueStatus.IN_PROGRESS.name -> {
                        Column() {
                            Icon(imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                tint = Color.DarkGray,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        viewModel.changeToDone(issue)
                                    })
                            Spacer(modifier = Modifier.height(16.dp))
                            Icon(imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.DarkGray,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        viewModel.changeToNextInProgress(issue)
                                    })
                        }


                    }

                    IssueStatus.DONE.name -> {
                        Icon(imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.DarkGray,
                            modifier = Modifier
                                .size(50.dp)
                                .clickable {
                                    viewModel.changeToDoneInProgress(issue)
                                })
                    }

                }
            }
        }

    }

}
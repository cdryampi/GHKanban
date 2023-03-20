package com.ircarren.ghkanban.domain.issue.add

import com.ircarren.ghkanban.domain.Repository
import com.ircarren.ghkanban.models.Issue
import javax.inject.Inject

class PutIssueListToPreferencesBacklog @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(issueList: List<Issue>, repo:String) = repository.putIssueListToPreferencesBacklog(issueList, repo)
}
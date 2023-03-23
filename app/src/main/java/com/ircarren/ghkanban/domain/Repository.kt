package com.ircarren.ghkanban.domain

import com.ircarren.ghkanban.data.enums.IssueStatus
import com.ircarren.ghkanban.models.Issue
import com.ircarren.ghkanban.models.Repository

interface Repository {
    suspend fun putListReposToPreferences(listRepos: com.ircarren.ghkanban.models.Repository)
    suspend fun getListReposFromPreferences(): List<String>
    suspend fun deleteListReposToPreferences(repo: Repository)
    suspend fun putIssueListToPreferencesBacklog(issueList: List<Issue>, repo: String, status: IssueStatus)
    suspend fun getIssueListFromPreferences(repo:String): List<Issue>
}
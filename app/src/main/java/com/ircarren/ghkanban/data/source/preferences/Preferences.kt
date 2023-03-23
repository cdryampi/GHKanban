package com.ircarren.ghkanban.data.source.preferences

import com.ircarren.ghkanban.data.enums.IssueStatus
import com.ircarren.ghkanban.models.Repository
import com.ircarren.ghkanban.models.Issue

interface Preferences {
    suspend fun getListReposFromPreferences(): List<String>
    suspend fun deleteListReposToPreferences(repo: Repository)
    suspend fun putRepoToPreferences(repo: Repository)
    suspend fun putIssueListToPreferencesBacklog(issueList: List<Issue>, repo: String, status: IssueStatus)
    suspend fun getIssueListFromPreferences(repo: String): List<Issue>
}
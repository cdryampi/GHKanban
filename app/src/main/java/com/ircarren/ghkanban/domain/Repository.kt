package com.ircarren.ghkanban.domain

import com.ircarren.ghkanban.models.Issue
import com.ircarren.ghkanban.models.Repository

interface Repository {
    suspend fun getReposForUser(username: String? ?=null): List<Repository>? ?=null
    suspend fun getReIssueForUser(username: String, repoName: String): List<Issue>
    suspend fun putListReposToPreferences(listRepos: com.ircarren.ghkanban.models.Repository)
    suspend fun getListReposFromPreferences(): List<String>
    suspend fun deleteListReposToPreferences(repo: Repository)
    suspend fun getIssuesFromGithub(repo: String): List<Issue>? ?=null
    suspend fun putIssueListToPreferencesBacklog(issueList: List<Issue>, repo: String)
    suspend fun getIssueListFromPreferences(repo:String): List<Issue>

}
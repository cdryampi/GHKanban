package com.ircarren.ghkanban.data.source.preferences

import com.ircarren.ghkanban.models.Issue
import com.ircarren.ghkanban.models.Repository

interface PreferencesNetwork {
    suspend fun getReposForUser(username: String): List<Repository>
    suspend fun getReIssueForUser(username: String, repoName: String): List<Issue>
    suspend fun getIssuesFromGithub(repo: String): List<Issue>
}
package com.ircarren.ghkanban.data.source.preferences

import com.ircarren.ghkanban.models.Repository
import com.ircarren.ghkanban.models.Issue

interface Preferences {
    suspend fun getReposForUser(username: String): List<Repository>
    suspend fun getReIssueForUser(username: String, repoName: String): List<Issue>
    suspend fun getListReposFromPreferences(): List<String>
    suspend fun deleteListReposToPreferences(repo: Repository)
    suspend fun putRepoToPreferences(repo: Repository)
}
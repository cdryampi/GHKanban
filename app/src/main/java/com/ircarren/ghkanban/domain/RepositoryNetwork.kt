package com.ircarren.ghkanban.domain

import com.ircarren.ghkanban.models.Issue
import com.ircarren.ghkanban.models.Repository

interface RepositoryNetwork {
    suspend fun getIssuesFromGithub(repo: String): List<Issue>? ?=null
    suspend fun getReposForUser(username: String? ?=null): List<Repository>? ?=null
    suspend fun getReIssueForUser(username: String, repoName: String): List<Issue>
}
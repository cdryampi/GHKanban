package com.ircarren.ghkanban.data.repository

import com.ircarren.ghkanban.data.source.preferences.Preferences
import com.ircarren.ghkanban.domain.Repository

import com.ircarren.ghkanban.models.Issue
import com.ircarren.ghkanban.util.GITHUB_DEFAULT_USER
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val preferences: Preferences,
) : Repository {
    override suspend fun getReposForUser(username: String?): List<com.ircarren.ghkanban.models.Repository>? {
        return preferences.getReposForUser(username = GITHUB_DEFAULT_USER)

    }

    override suspend fun getReIssueForUser(username: String, repoName: String): List<Issue> {
        TODO("Not yet implemented")
    }

    override suspend fun putListReposToPreferences(repo: com.ircarren.ghkanban.models.Repository) {
        preferences.putRepoToPreferences(repo)
    }


    override suspend fun getListReposFromPreferences(): List<String> {
        return preferences.getListReposFromPreferences()
    }

    override suspend fun deleteListReposToPreferences(repo: com.ircarren.ghkanban.models.Repository) {
        preferences.deleteListReposToPreferences(repo)
    }

    override suspend fun getIssuesFromGithub(repo: String): List<Issue> {
        return preferences.getIssuesFromGithub(repo)
    }

    override suspend fun putIssueListToPreferencesBacklog(issueList: List<Issue>, repo: String) {
        return preferences.putIssueListToPreferencesBacklog(issueList, repo)
    }

    override suspend fun getIssueListFromPreferences(repo: String): List<Issue> {
        return preferences.getIssueListFromPreferences(repo)
    }

}

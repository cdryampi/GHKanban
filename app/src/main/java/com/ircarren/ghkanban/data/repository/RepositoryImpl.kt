package com.ircarren.ghkanban.data.repository

import com.ircarren.ghkanban.data.enums.IssueStatus
import com.ircarren.ghkanban.data.source.preferences.Preferences
import com.ircarren.ghkanban.domain.Repository

import com.ircarren.ghkanban.models.Issue
import com.ircarren.ghkanban.util.GITHUB_DEFAULT_USER
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val preferences: Preferences,
) : Repository {
    override suspend fun putListReposToPreferences(repo: com.ircarren.ghkanban.models.Repository) {
        preferences.putRepoToPreferences(repo)
    }


    override suspend fun getListReposFromPreferences(): List<String> {
        return preferences.getListReposFromPreferences()
    }

    override suspend fun deleteListReposToPreferences(repo: com.ircarren.ghkanban.models.Repository) {
        preferences.deleteListReposToPreferences(repo)
    }



    override suspend fun putIssueListToPreferencesBacklog(issueList: List<Issue>, repo: String, status: IssueStatus) {
        return preferences.putIssueListToPreferencesBacklog(issueList, repo, status)
    }

    override suspend fun getIssueListFromPreferences(repo: String): List<Issue> {
        return preferences.getIssueListFromPreferences(repo)
    }

}

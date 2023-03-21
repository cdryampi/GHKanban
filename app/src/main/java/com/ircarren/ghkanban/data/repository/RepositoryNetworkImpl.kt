package com.ircarren.ghkanban.data.repository

import com.ircarren.ghkanban.data.source.preferences.PreferencesNetwork
import com.ircarren.ghkanban.domain.RepositoryNetwork
import com.ircarren.ghkanban.models.Issue
import com.ircarren.ghkanban.util.GITHUB_DEFAULT_USER
import javax.inject.Inject

class RepositoryNetworkImpl @Inject constructor(
    private val preferencesNetwork: PreferencesNetwork
): RepositoryNetwork {


    override suspend fun getReposForUser(username: String?): List<com.ircarren.ghkanban.models.Repository>? {
        return preferencesNetwork.getReposForUser(username = GITHUB_DEFAULT_USER)

    }

    override suspend fun getReIssueForUser(username: String, repoName: String): List<Issue> {
        TODO("Not yet implemented")
    }

    override suspend fun getIssuesFromGithub(repo: String): List<Issue> {
        return preferencesNetwork.getIssuesFromGithub(repo)
    }
}
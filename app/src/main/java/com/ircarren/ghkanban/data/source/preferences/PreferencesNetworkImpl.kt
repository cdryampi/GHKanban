package com.ircarren.ghkanban.data.source.preferences

import android.content.Context
import com.ircarren.ghkanban.data.controllers.GithubRepository
import com.ircarren.ghkanban.models.Issue
import com.ircarren.ghkanban.models.Repository
import com.ircarren.ghkanban.util.GITHUB_DEFAULT_USER
import javax.inject.Inject

class PreferencesNetworkImpl @Inject constructor(
    private val context : Context
): PreferencesNetwork{
    override suspend fun getReposForUser(username: String): List<Repository> {
        val githubRepository = GithubRepository()
        var reposfromGithub = githubRepository.getReposForUser(username)
        reposfromGithub = reposfromGithub.filter { it.name.length > 5 }
        return reposfromGithub

    }

    override suspend fun getReIssueForUser(username: String, repoName: String): List<Issue> {
        TODO()
    }
    override suspend  fun getIssuesFromGithub(repo: String): List<Issue> {
        val githubRepository = GithubRepository()
        return githubRepository.getReIssueForUser(GITHUB_DEFAULT_USER, repo)
    }
}
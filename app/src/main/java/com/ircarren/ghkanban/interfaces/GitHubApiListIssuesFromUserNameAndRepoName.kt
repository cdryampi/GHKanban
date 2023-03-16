package com.ircarren.ghkanban.interfaces

import com.ircarren.ghkanban.models.Issue
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiListIssuesFromUserNameAndRepoName {
    @GET("/repos/{owner}/{repo}/issues")
    suspend fun getIssuesForUserAndRepo(
        @Path("owner") username: String,
        @Path("repo") repo: String
    ): List<Issue>
}
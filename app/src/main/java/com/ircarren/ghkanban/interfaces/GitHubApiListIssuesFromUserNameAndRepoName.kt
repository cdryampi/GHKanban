package com.ircarren.ghkanban.interfaces

import com.ircarren.ghkanban.models.Issue
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiListIssuesFromUserNameAndRepoName {
    @GET("/repos/{username}/{repo}/issues")
    suspend fun getIssuesForUserAndRepo(
        @Path("username") username: String,
        @Path("repo") repo: String
    ): List<Issue>
}
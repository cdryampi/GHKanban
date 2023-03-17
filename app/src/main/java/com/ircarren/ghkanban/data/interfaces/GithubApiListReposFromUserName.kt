package com.ircarren.ghkanban.data.interfaces

import com.ircarren.ghkanban.models.Repository
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiListReposFromUserName {
    @GET("/users/{username}/repos")
    suspend fun getReposForUser(
        @Path("username") username: String
    ): List<Repository>
}
package com.ircarren.ghkanban.controllers

import com.ircarren.ghkanban.interfaces.GithubApiListReposFromUserName
import com.ircarren.ghkanban.models.Repository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubRepository {

    private val githubApi: GithubApiListReposFromUserName by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApiListReposFromUserName::class.java)
    }

    suspend fun getReposForUser(username: String): List<Repository> {
        return githubApi.getReposForUser(username)
    }

}
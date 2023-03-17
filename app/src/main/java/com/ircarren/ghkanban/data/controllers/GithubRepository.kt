package com.ircarren.ghkanban.data.controllers

import com.ircarren.ghkanban.data.interfaces.GitHubApiListIssuesFromUserNameAndRepoName
import com.ircarren.ghkanban.data.interfaces.GithubApiListReposFromUserName
import com.ircarren.ghkanban.models.Issue
import com.ircarren.ghkanban.models.Repository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GithubRepository {


    private val client = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "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")
            .build()
        chain.proceed(request)
    }.build()


    private val githubApi: GithubApiListReposFromUserName by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApiListReposFromUserName::class.java)
    }

    private val githubApiClient: GitHubApiListIssuesFromUserNameAndRepoName by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApiListIssuesFromUserNameAndRepoName::class.java)
    }


    suspend fun getReposForUser(username: String): List<Repository> {
        return githubApi.getReposForUser(username)
    }

    suspend fun getReIssueForUser(username: String, repoName: String): List<Issue> {
        return githubApiClient.getIssuesForUserAndRepo(username, repoName)
    }

}

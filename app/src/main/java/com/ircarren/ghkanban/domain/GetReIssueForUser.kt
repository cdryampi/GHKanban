package com.ircarren.ghkanban.domain

import javax.inject.Inject

class GetReIssueForUser @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(userName:String,repoName:String) = repository.getReIssueForUser(username = userName,repoName = repoName)
}
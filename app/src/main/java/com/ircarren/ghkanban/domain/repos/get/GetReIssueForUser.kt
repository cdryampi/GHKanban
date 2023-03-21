package com.ircarren.ghkanban.domain.repos.get

import com.ircarren.ghkanban.domain.Repository
import com.ircarren.ghkanban.domain.RepositoryNetwork
import javax.inject.Inject

class GetReIssueForUser @Inject constructor(
    private val repository: RepositoryNetwork
) {
    suspend operator fun invoke(userName:String,repoName:String) = repository.getReIssueForUser(username = userName,repoName = repoName)
}
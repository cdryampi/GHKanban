package com.ircarren.ghkanban.domain.issue.get

import com.ircarren.ghkanban.domain.Repository
import com.ircarren.ghkanban.domain.RepositoryNetwork
import javax.inject.Inject

class GetIssuesFromGithub @Inject constructor(
    private val repository: RepositoryNetwork
) {
    suspend operator fun invoke(repoName:String) = repository.getIssuesFromGithub(repoName)
}
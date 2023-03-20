package com.ircarren.ghkanban.domain.issue.get

import com.ircarren.ghkanban.domain.Repository
import javax.inject.Inject

class GetIssuesFromGithub @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(repoName:String) = repository.getIssuesFromGithub(repoName)
}
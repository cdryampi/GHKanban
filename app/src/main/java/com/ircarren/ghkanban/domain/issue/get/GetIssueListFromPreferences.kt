package com.ircarren.ghkanban.domain.issue.get

import com.ircarren.ghkanban.domain.Repository
import javax.inject.Inject

class GetIssueListFromPreferences @Inject constructor(
    private val repository: Repository
){
    suspend operator fun invoke(repo:String) = repository.getIssueListFromPreferences(repo)
}
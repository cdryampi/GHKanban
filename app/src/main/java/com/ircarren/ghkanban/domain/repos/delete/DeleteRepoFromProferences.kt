package com.ircarren.ghkanban.domain.repos.delete

import com.ircarren.ghkanban.domain.Repository
import javax.inject.Inject

class DeleteRepoFromProferences @Inject constructor(
    private val repository: Repository
){
    suspend operator fun invoke(repo: com.ircarren.ghkanban.models.Repository) = repository.deleteListReposToPreferences(repo)
}
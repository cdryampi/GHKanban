package com.ircarren.ghkanban.domain.repos.add

import com.ircarren.ghkanban.domain.Repository
import javax.inject.Inject

class PutRepoToPreferences @Inject constructor(
    private val repository: Repository
){
    suspend operator fun invoke(repo: com.ircarren.ghkanban.models.Repository) = repository.putListReposToPreferences(repo)
}
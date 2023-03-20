package com.ircarren.ghkanban.domain

import javax.inject.Inject

class PutRepoToPreferences @Inject constructor(
    private val repository: Repository
){
    suspend operator fun invoke(repo: com.ircarren.ghkanban.models.Repository) = repository.putListReposToPreferences(repo)
}
package com.ircarren.ghkanban.domain.repos.get

import com.ircarren.ghkanban.domain.Repository
import javax.inject.Inject

class GetReposFromGithub @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(userName:String) = repository.getReposForUser(userName)
}
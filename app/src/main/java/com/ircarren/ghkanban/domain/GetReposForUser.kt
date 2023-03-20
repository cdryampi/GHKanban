package com.ircarren.ghkanban.domain

import javax.inject.Inject

class GetReposForUser @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(userName: String) = repository.getReposForUser(username = userName)
}
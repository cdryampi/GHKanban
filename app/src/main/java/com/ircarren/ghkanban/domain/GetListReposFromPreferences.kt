package com.ircarren.ghkanban.domain

import javax.inject.Inject

class GetListReposFromPreferences @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke() = repository.getListReposFromPreferences()
}
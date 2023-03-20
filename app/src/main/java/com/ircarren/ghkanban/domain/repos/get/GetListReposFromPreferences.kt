package com.ircarren.ghkanban.domain.repos.get

import com.ircarren.ghkanban.domain.Repository
import javax.inject.Inject

class GetListReposFromPreferences @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke() = repository.getListReposFromPreferences()
}
package com.ircarren.ghkanban.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ircarren.ghkanban.models.Repo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class RepoLocal: ViewModel() {
    private val _repoIdsLocal = MutableLiveData<List<Repo>>()
    val repoIdsLocal: LiveData<List<Repo>> = _repoIdsLocal

    fun addRepoLocal(repo: Repo) {
       _repoIdsLocal.value = _repoIdsLocal.value?.plus(repo) ?: listOf(repo)
    }
    fun deleteRepoLocal(repo: Repo) {
        _repoIdsLocal.value = _repoIdsLocal.value?.minus(repo) ?: listOf()
    }
}
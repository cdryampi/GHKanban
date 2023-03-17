package com.ircarren.ghkanban.ui.viewModel

import android.app.Application
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import com.ircarren.ghkanban.data.controllers.GithubRepository
import com.ircarren.ghkanban.data.controllers.SharedLocalStorageManager
import com.ircarren.ghkanban.models.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RepoLocalViewModel(application: Application) : AndroidViewModel(application) {

    private val SharedPref = SharedLocalStorageManager(application)


    // Repositorio local guardado
    private val _repoIdsLocal = MutableLiveData<List<String>>()
    val repoIdsLocal: LiveData<List<String>> = _repoIdsLocal


    // Repositorio
    private val repository = GithubRepository()
    private val _repos = MutableLiveData<List<Repository>>()
    val repos: LiveData<List<Repository>> = _repos


    init {
        getReposForUser()
        loadRepoLocal()
    }

    // cosas de github
    fun getReposForUser(username: String="cdryampi") {
        viewModelScope.launch {
            try {
                val deferred = async {
                    repository.getReposForUser(username)
                }
                _repos.value = deferred.await()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }


    // Guarda el repositorio en el shared preferences
     fun saveRepoLocal(repo: String) {
        SharedPref.saveRepoLocal(repo)
        _repoIdsLocal.value = SharedPref.getRepoLocal()
    }

     private fun loadRepoLocal() {
        SharedPref.loadRepoLocal()
        _repoIdsLocal.value = SharedPref.getRepoLocal()
    }

     fun deleteOneRefRepoLocal(RemoveRepo: String) {
        SharedPref.deleteOneRefRepoLocal(RemoveRepo)
        _repoIdsLocal.value = SharedPref.getRepoLocal()
    }

}
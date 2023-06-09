package com.ircarren.ghkanban.ui.viewModel


import androidx.lifecycle.*
import com.ircarren.ghkanban.domain.repos.delete.DeleteRepoFromProferences
import com.ircarren.ghkanban.domain.repos.get.GetListReposFromPreferences
import com.ircarren.ghkanban.domain.repos.get.GetReposForUser
import com.ircarren.ghkanban.domain.repos.add.PutRepoToPreferences
import com.ircarren.ghkanban.models.Repository
import com.ircarren.ghkanban.ui.MyRepoEvent
import com.ircarren.ghkanban.util.GITHUB_DEFAULT_USER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async

import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RepoLocalViewModel @Inject constructor(
    private val getListReposFromPreferences: GetListReposFromPreferences,
    private val putRepoToPreferences: PutRepoToPreferences,
    private val deleteRepoFromProferences: DeleteRepoFromProferences,
    private val getReposForUser: GetReposForUser
) :ViewModel(){

        // variables para el repositorio de github

        private var _listRepos: MutableLiveData<List<Repository>> = MutableLiveData()
        val listRepos: LiveData<List<Repository>> = _listRepos

        // variables para el repositorio de preferencias
        private var _listReposFromPreferences: MutableLiveData<List<String>> = MutableLiveData()
        val listReposFromPreferences: LiveData<List<String>> = _listReposFromPreferences

        init {
            getListReposFromGithub()
            getListReposFromPreferences()
        }

        fun onEvent(event: MyRepoEvent) {
        }

        fun addRepo(repo: Repository) {
            viewModelScope.launch {
                putRepoToPreferences.invoke(repo = repo)
            }.invokeOnCompletion {
                getListReposFromPreferences()
            }
        }
        private fun getListReposFromPreferences() {
            viewModelScope.launch {
                val list = async { getListReposFromPreferences.invoke() }
                val listReposFromPreferences = list.await()
                _listReposFromPreferences.value = listReposFromPreferences

            }.invokeOnCompletion {
                println("listReposFromPreferences: ${_listReposFromPreferences.value}")
            }
        }
        fun deleteListReposToPreferences(repo: Repository) {
            viewModelScope.launch {
                //putListReposToPreferences.invoke(listOf())
                deleteRepoFromProferences.invoke(repo)
                getListReposFromPreferences()
            }
        }

        private fun getListReposFromGithub() {
            viewModelScope.launch {
                val listReposFromGithub = getReposForUser.invoke(GITHUB_DEFAULT_USER)
                _listRepos.value = listReposFromGithub
                println(_listRepos.value)
            }
        }

        fun GetListReposFromPreferences(): GetListReposFromPreferences {
            return getListReposFromPreferences
        }
        fun GetListReposfromGithub(): GetReposForUser {
            return getReposForUser
        }

}
package com.ircarren.ghkanban.ui.viewModel

import androidx.lifecycle.*
import com.ircarren.ghkanban.data.enums.IssueStatus
import com.ircarren.ghkanban.domain.issue.add.PutIssueListToPreferencesBacklog
import com.ircarren.ghkanban.domain.issue.get.GetIssueListFromPreferences
import com.ircarren.ghkanban.domain.issue.get.GetIssuesFromGithub
import com.ircarren.ghkanban.models.Issue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueViewModel @Inject constructor(
    private val getIssuesFromGithub: GetIssuesFromGithub,
    private val savedStateHandle: SavedStateHandle,
    private val getIssueListFromPreferences: GetIssueListFromPreferences,
    private val putIssueListToPreferences: PutIssueListToPreferencesBacklog

) : ViewModel() {

    // Repositorio de issues total
    private val _issueBacklogLocal = MutableLiveData<List<Issue>>()
    val issueBacklogLocal: LiveData<List<Issue>> = _issueBacklogLocal

    // Repositorio de issues en next
    private val _issueNextLocal = MutableLiveData<List<Issue>>()
    val issueNextLocal: LiveData<List<Issue>> = _issueNextLocal

    // Repositorio de issues en progreso
    private val _issueInProgressLocal = MutableLiveData<List<Issue>>()
    val issueInProgressLocal: LiveData<List<Issue>> = _issueInProgressLocal

    // Repositorio de issues en done
    private val _issueDoneLocal = MutableLiveData<List<Issue>>()
    val issueDoneLocal: LiveData<List<Issue>> = _issueDoneLocal


    // funcion para cambiar de estado de issue al siguiente
    fun changeToNext(issue: Issue) {
        viewModelScope.launch {
            when (issue.status?.name) {
                IssueStatus.BACKLOG.name -> {
                    issue.status = IssueStatus.NEXT
                    _issueBacklogLocal.value =
                        (_issueBacklogLocal.value?.minus(issue) ?: listOf(issue)) as List<Issue>?

                    if (_issueBacklogLocal.value?.contains(issue) == true) {
                        _issueBacklogLocal.value =
                            (_issueBacklogLocal.value?.minus(issue) ?: listOf(issue)) as List<Issue>?
                    }
                    _issueNextLocal.value =
                        (_issueNextLocal.value?.plus(issue) ?: listOf(issue)) as List<Issue>?

                    // bug de duplicados en el listado cuando se cambia de estado desde el backlog a next
                    _issueNextLocal.value = _issueNextLocal.value?.distinct()
                    println("changeToNext")
                }
                IssueStatus.NEXT.name -> {
                    issue.status = IssueStatus.IN_PROGRESS
                    _issueNextLocal.value =
                        (_issueNextLocal.value?.minus(issue) ?: listOf(issue)) as List<Issue>?
                    _issueInProgressLocal.value =
                        (_issueInProgressLocal.value?.plus(issue) ?: listOf(issue)) as List<Issue>?
                    println("changeToInProgress")
                }
                IssueStatus.IN_PROGRESS.name -> {
                    issue.status = IssueStatus.DONE
                    _issueInProgressLocal.value =
                        (_issueInProgressLocal.value?.minus(issue) ?: listOf(issue)) as List<Issue>?
                    _issueDoneLocal.value =
                        (_issueDoneLocal.value?.plus(issue) ?: listOf(issue)) as List<Issue>?
                    println("changeToDone")
                }
            }
        }.invokeOnCompletion {
            putIssueListToPreferences()
        }

    }


    // funcion para cambiar de estado de issue al anterior
    fun changeToPrev(issue: Issue) {
        viewModelScope.launch {
            when(issue.status?.name){
                IssueStatus.NEXT.name->{
                    issue.status = IssueStatus.BACKLOG
                    _issueNextLocal.value =
                        (_issueNextLocal.value?.minus(issue) ?: listOf(issue)) as List<Issue>?
                    _issueBacklogLocal.value =
                        (_issueBacklogLocal.value?.plus(issue) ?: listOf(issue)) as List<Issue>?
                    println("changetoBacklog")
                }
                IssueStatus.IN_PROGRESS.name->{
                    issue.status = IssueStatus.NEXT
                    _issueInProgressLocal.value =
                        (_issueInProgressLocal.value?.minus(issue) ?: listOf(issue)) as List<Issue>?
                    _issueNextLocal.value =
                        (_issueNextLocal.value?.plus(issue) ?: listOf(issue)) as List<Issue>?
                    println("changeToNextInProgress")
                }
                IssueStatus.DONE.name->{
                    issue.status = IssueStatus.IN_PROGRESS
                    _issueDoneLocal.value =
                        (_issueDoneLocal.value?.minus(issue) ?: listOf(issue)) as List<Issue>?
                    _issueInProgressLocal.value =
                        (_issueInProgressLocal.value?.plus(issue) ?: listOf(issue)) as List<Issue>?
                    println("changeToDoneInProgress")
                }
            }
        }.invokeOnCompletion {
            putIssueListToPreferences()
        }

    }

    // Issues
    private val _issues = MutableLiveData<List<Issue>>()
    val issues: LiveData<List<Issue>> = _issues

    // Invokar el caso de uso para obtener las issues
    private fun getIssuesFromGithubLocal(repoName: String) {
        viewModelScope.launch {
            val listIssuesFromGithub = getIssuesFromGithub.invoke(repoName)
            _issues.value = listIssuesFromGithub
            if (listIssuesFromGithub != null) {
                _issueBacklogLocal.value = listIssuesFromGithub.filter { it.status?.name == IssueStatus.BACKLOG.name }
            }
        }
    }


    fun getRepoName(): String {
        return getRepoNameFromParams()
    }

    fun setRepoName(repoName: String) {
        savedStateHandle["repoName"] = repoName
    }
    private fun getRepoNameFromParams(): String {
        return savedStateHandle["repoName"] ?: "a"
    }

    fun callAPI(){
        getIssuesFromDataStoreLocal()
        if (_issues.value == null || _issues.value?.isEmpty() == true || _issues.value?.size == 0){
            getIssuesFromGithubLocal(getRepoName())
        }else{
            println("No se llama a la API")
        }
    }

    // CRUD
    private fun getIssuesFromDataStoreLocal() {
        viewModelScope.launch {
            val listIssuesFromDataStore = getIssueListFromPreferences.invoke(getRepoName())
            _issues.value = listIssuesFromDataStore
            println("getIssuesFromDataStoreLocal $listIssuesFromDataStore")
        }.invokeOnCompletion {
            if (_issues.value != null) {
                _issueBacklogLocal.value = _issues.value?.filter { it.status?.name == IssueStatus.BACKLOG.name }
                _issueNextLocal.value = _issues.value?.filter { it.status?.name == IssueStatus.NEXT.name }
                _issueInProgressLocal.value = _issues.value?.filter { it.status?.name == IssueStatus.IN_PROGRESS.name }
                _issueDoneLocal.value = _issues.value?.filter { it.status?.name == IssueStatus.DONE.name }
            }
        }
    }


    private fun putIssueListToPreferences(){
        viewModelScope.launch {
            putIssueListToPreferences.invoke(_issueBacklogLocal.value ?: listOf(), getRepoName())
            putIssueListToPreferences.invoke(_issueNextLocal.value ?: listOf(), getRepoName())
            putIssueListToPreferences.invoke(_issueInProgressLocal.value ?: listOf(), getRepoName())
            putIssueListToPreferences.invoke(_issueDoneLocal.value ?: listOf(), getRepoName())
        }.invokeOnCompletion {
            println("putIssueListToPreferences completed")
        }
    }

    fun clearIssueListFromModelView(){
        viewModelScope.launch {
            _issues.value = listOf()
            _issueBacklogLocal.value = listOf()
            _issueNextLocal.value = listOf()
            _issueInProgressLocal.value = listOf()
            _issueDoneLocal.value = listOf()
        }.invokeOnCompletion {

        }

    }



}
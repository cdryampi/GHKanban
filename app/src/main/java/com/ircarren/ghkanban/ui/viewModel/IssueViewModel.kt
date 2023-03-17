package com.ircarren.ghkanban.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ircarren.ghkanban.data.controllers.GithubRepository
import com.ircarren.ghkanban.data.enums.IssueStatus
import com.ircarren.ghkanban.models.Issue
import kotlinx.coroutines.launch

class IssueViewModel(username: String, repoName: String) : ViewModel() {

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
    }


    // funcion para cambiar de estado de issue al anterior
    fun changeToPrev(issue: Issue) {
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
    }

    // Issues
    private val repository = GithubRepository()
    private val _issues = MutableLiveData<List<Issue>>()
    val issues: LiveData<List<Issue>> = _issues

    init {
        getIssuesForUser(username = username, repo = repoName)
    }


    fun getIssuesForUser(username: String = "cdryampi", repo: String = "LaSagradaFamilia") {
        viewModelScope.launch {
            repository.getReIssueForUser(username, repo).let { it ->
                _issues.value = it
                _issueBacklogLocal.value = it.filter { it.status == IssueStatus.BACKLOG }
                println("issues: $it")
            }
        }
    }
}
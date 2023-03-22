package com.ircarren.ghkanban.data.source.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ircarren.ghkanban.data.enums.IssueStatus
import com.ircarren.ghkanban.data.localDataBase.DataBaseBuilder
import com.ircarren.ghkanban.models.Repository
import com.ircarren.ghkanban.models.Issue
import com.ircarren.ghkanban.util.KEY_REPO_ISSUES
import com.ircarren.ghkanban.util.KEY_SHARED_PREF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import javax.inject.Inject


private val Context.dataStore by preferencesDataStore(name = KEY_SHARED_PREF)

class PreferencesImpl @Inject constructor(
    private val context: Context
) : Preferences {

    override suspend fun putRepoToPreferences(repo: com.ircarren.ghkanban.models.Repository) {

        val preferenceKey = stringPreferencesKey(KEY_SHARED_PREF)
        context.dataStore.edit { preferences ->

            preferences[preferenceKey]?.split(",")?.find { it == repo.name }?.let {
                // ya existe
                println("Reference already exists")
            } ?: run {
                // no existe
                val newListRepos: MutableList<String> = mutableListOf()
                preferences[preferenceKey]?.split(",")?.let { list ->
                    newListRepos.addAll(list)
                }
                newListRepos.add(repo.name)
                preferences[preferenceKey] = newListRepos.joinToString(",")
                println("Reference added")
            }
            println("preferences[preferenceKey] ${preferences[preferenceKey]}")
        }
    }

    override suspend fun putIssueListToPreferencesBacklog(issueList: List<Issue>, repo: String) {

        val issueDaoController = DataBaseBuilder.getInstance(context).issueDao()


        println("listfromlocalDB ${issueList.toList()}")
        var issueListToInsert = mutableListOf<com.ircarren.ghkanban.data.localDataBase.dao.Issue>()

        issueList.forEach { _issue ->
            val newIssue = _issue.title?.let {
                _issue.body?.let { it1 ->
                    _issue.status?.let { it2 ->
                        com.ircarren.ghkanban.data.localDataBase.dao.Issue(
                            title = it,
                            body = _issue.body,
                            status = it1,
                            repo = repo
                        )
                    }
                }
            }

            if (newIssue != null) {
                issueListToInsert.add(newIssue)
            }
        }


        withContext(Dispatchers.IO) {
            issueListToInsert.forEach { _issue ->
                try {
                    issueDaoController.insertOne(
                        _issue.title,
                        _issue.body,
                        _issue.status,
                        _issue.repo
                    )
                } catch (e: Exception) {
                    try {
                        issueDaoController.updateOne(
                            _issue.title,
                            _issue.status,
                            _issue.repo
                        )
                        println("updated")
                    } catch (e: Exception) {
                        println("error ${e.message}")
                    }
                    println("error ${e.message}")
                }


            }

        }


        /*val preferenceKey = stringPreferencesKey(repo + KEY_REPO_ISSUES)

        val newListIssues: MutableList<String> = mutableListOf()
        context.dataStore.edit { preferences ->
            issueList.forEach { issue ->
                val issue = "${issue.title}#${issue.status}#${issue.body}"
                newListIssues.add(issue)
            }
            preferences[preferenceKey] = newListIssues.joinToString(",")
            println("preferences[preferenceKey] ${preferences[preferenceKey]}")
        }*/
    }

    override suspend fun getIssueListFromPreferences(repo: String): List<Issue> {
        val issueDaoController = DataBaseBuilder.getInstance(context).issueDao()
        val allIssues = withContext(Dispatchers.IO) {
            issueDaoController.loadAllByRepo(repo)
        }
        var issueList = mutableListOf<Issue>()
        allIssues.forEach { _issue ->
            var status = IssueStatus.BACKLOG
            when (_issue.status) {
                "BACKLOG" -> status = IssueStatus.BACKLOG
                "NEXT" -> status = IssueStatus.NEXT
                "INPROGRESS" -> status = IssueStatus.IN_PROGRESS
                "DONE" -> status = IssueStatus.DONE
            }
            val newIssue = _issue.title?.let {
                _issue.body?.let { it1 ->
                    Issue(
                        title = it,
                        status = status,
                        body = it1,
                    )
                }
            }

            if (newIssue != null) {
                issueList.add(newIssue)
            }
        }
        /*
        val preferenceKey = stringPreferencesKey(repo + KEY_REPO_ISSUES)

        val preferences = context.dataStore.data.toList()
        val issueList: MutableList<Issue> = mutableListOf()

        preferences.forEach {
            it[preferenceKey]?.split(",")?.forEach { issue ->
                println("issue: $issue")
            }
        }
        println("issueList: $issueList")
        return issueList*/
        return issueList
    }

    override suspend fun getListReposFromPreferences(): List<String> {
        return try {
            val preferenceKey = stringPreferencesKey(KEY_SHARED_PREF)
            val preferences = context.dataStore.data.first()
            val listRepos = preferences[preferenceKey]
            listRepos?.split(",") ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun deleteListReposToPreferences(repo: Repository) {
        // eliminar referencia de la lista de repositorios locales

        val newListRepos: MutableList<String> = mutableListOf()

        val preferenceKey = stringPreferencesKey(KEY_SHARED_PREF)

        val issueDaoController = DataBaseBuilder.getInstance(context).issueDao()

        withContext(Dispatchers.IO) {
            issueDaoController.deleteAllByRepo(repo.name)
        }

        context.dataStore.edit { preferences ->
            preferences[preferenceKey]?.split(",")?.forEach {

                if (it != repo.name) {
                    newListRepos.add(it)
                }
            }

        }
        context.dataStore.edit { preferences ->
            preferences[preferenceKey] = newListRepos.joinToString(",")
        }
    }

}
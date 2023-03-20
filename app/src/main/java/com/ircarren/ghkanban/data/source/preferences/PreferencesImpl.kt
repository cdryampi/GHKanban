package com.ircarren.ghkanban.data.source.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ircarren.ghkanban.data.controllers.GithubRepository
import com.ircarren.ghkanban.models.Repository
import com.ircarren.ghkanban.models.Issue
import com.ircarren.ghkanban.util.GITHUB_DEFAULT_USER
import com.ircarren.ghkanban.util.KEY_REPO_ISSUES
import com.ircarren.ghkanban.util.KEY_SHARED_PREF
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import javax.inject.Inject


private val Context.dataStore by preferencesDataStore(name = KEY_SHARED_PREF)

class PreferencesImpl @Inject constructor(
    private val context: Context
) : Preferences {
    override suspend fun getReposForUser(username: String): List<Repository> {
        val githubRepository = GithubRepository()
        return githubRepository.getReposForUser(username)

    }

    override suspend fun getReIssueForUser(username: String, repoName: String): List<Issue> {
        TODO("Not yet implemented")
    }


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

    override suspend fun getIssuesFromGithub(repo: String): List<Issue> {
        val githubRepository = GithubRepository()
        return githubRepository.getReIssueForUser(GITHUB_DEFAULT_USER, repo)
    }

    override suspend fun putIssueListToPreferencesBacklog(issueList: List<Issue>, repo: String) {
        val preferenceKey = stringPreferencesKey(repo + KEY_REPO_ISSUES)

        val newListIssues: MutableList<String> = mutableListOf()
        context.dataStore.edit { preferences ->
            issueList.forEach { issue ->
                val issue = "${issue.title}#${issue.status}#${issue.body}"
                newListIssues.add(issue)
            }
            preferences[preferenceKey] = newListIssues.joinToString(",")
            println("preferences[preferenceKey] ${preferences[preferenceKey]}")
        }

    }

    override suspend fun getIssueListFromPreferences(repo: String): List<Issue> {
        val preferenceKey = stringPreferencesKey(repo + KEY_REPO_ISSUES)

        val preferences = context.dataStore.data.toList()
        val issueList: MutableList<Issue> = mutableListOf()

        preferences.forEach {
            it[preferenceKey]?.split(",")?.forEach { issue ->
                println("issue: $issue")
            }
        }
        println("issueList: $issueList")
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

        val child_preferenceKey = stringPreferencesKey(repo.name + KEY_REPO_ISSUES)

        if (context.dataStore.data.first()[child_preferenceKey] != null){
            context.dataStore.edit {
                it.remove(child_preferenceKey)
            }

        }else{
            println("No existe la referencia")
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
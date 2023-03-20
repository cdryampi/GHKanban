package com.ircarren.ghkanban.data.source.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ircarren.ghkanban.data.controllers.GithubRepository
import com.ircarren.ghkanban.models.Repository
import com.ircarren.ghkanban.models.Issue
import com.ircarren.ghkanban.util.KEY_SHARED_PREF
import kotlinx.coroutines.flow.first
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
            val listRepos = preferences[preferenceKey]?.split(",") ?: emptyList()
            val newListRepos = listRepos.toMutableList()
            newListRepos.add(repo.name)
            preferences[preferenceKey] = newListRepos.joinToString(",")
        }

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
        /*
        context.dataStore.edit {preferences ->
            preferences.remove(preferenceKey)
         }

         */
    }
}
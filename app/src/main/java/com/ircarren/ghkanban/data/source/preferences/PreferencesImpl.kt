package com.ircarren.ghkanban.data.source.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ircarren.ghkanban.data.enums.IssueStatus
import com.ircarren.ghkanban.data.localDataBase.DataBaseBuilder
import com.ircarren.ghkanban.models.Issue
import com.ircarren.ghkanban.models.Repository
import com.ircarren.ghkanban.util.KEY_REPO_ISSUES
import com.ircarren.ghkanban.util.KEY_SHARED_PREF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
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

    override suspend fun putIssueListToPreferencesBacklog(
        issueList: List<Issue>,
        repo: String,
        status: IssueStatus
    ) {
        /*
        val preferenceKey = stringPreferencesKey(repo + KEY_REPO_ISSUES+status.name)

        val newListIssues: MutableList<String> = mutableListOf()
        val gson = Gson()

        context.dataStore.edit { preferences ->
            var json = gson.toJson(issueList)
            preferences[preferenceKey] = json
            println("preferences[preferenceKey] ${preferences[preferenceKey]}")
        }
         */

        val db = DataBaseBuilder.getInstance(context)

        println("IssueList size: ${issueList.size}")
        issueList.toList().forEach {

            var nodeId = it.id
            var status = if (it.state != "open" && it.state != "closed") it.state else status.name



            println("Issue: $nodeId)")
            if (nodeId != null) {
                try {
                    val issue = withContext(Dispatchers.IO) {
                        db.issueDao().loadById(nodeId)
                    }
                    withContext(Dispatchers.IO) {
                        if (issue != null) {
                            status?.let { it1 ->
                                com.ircarren.ghkanban.data.localDataBase.dao.Issue(
                                    it.id!!,
                                    it.title!!,
                                    it.body!!,
                                    it1,
                                    repo)
                            }?.let { it2 ->
                                db.issueDao().updateOne(
                                    it2
                                )
                            }
                            println("Issue updated")
                        } else {
                            status?.let { it1 ->
                                com.ircarren.ghkanban.data.localDataBase.dao.Issue(
                                    it.id!!,
                                    it.title!!,
                                    it.body!!,
                                    it1,
                                    repo)
                            }?.let { it2 ->
                                db.issueDao().insertOne(
                                    it2
                                )
                            }
                            println("Issue added")
                        }
                    }

                } catch (e: Exception) {
                    println("Error: ${e.message}")
                }
            }
        }

    }

    override suspend fun getIssueListFromPreferences(repo: String): List<Issue> {
        var issueList: List<Issue> = emptyList()
        /*
        for (status in IssueStatus.values()) {
            val preferenceKey = stringPreferencesKey(repo + KEY_REPO_ISSUES + status.name)

            val preferences = context.dataStore.data.first()
            val gson = Gson()
            val json = preferences[preferenceKey]
            val issueListJson = gson.fromJson(json, Array<Issue>::class.java)
            issueList = issueList.plus(issueListJson?.toList() ?: emptyList())
        }*/

        val db = DataBaseBuilder.getInstance(context)
        for (status in IssueStatus.values()) {
            withContext(Dispatchers.IO) {
                db.issueDao().loadAllByRepo(repo)
            }.forEach {
                issueList = issueList.plus(Issue(it.id, it.title, it.body, it.status, it.repo))
            }
        }

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

        val db = DataBaseBuilder.getInstance(context)
        for (status in IssueStatus.values()) {
            val preferenceKeyIssues =
                stringPreferencesKey(repo.name + KEY_REPO_ISSUES + status.name)
            context.dataStore.edit { preferences ->
                preferences[preferenceKeyIssues] = ""
            }
        }

        withContext(Dispatchers.IO) {
            db.issueDao().deleteAllByRepo(repo.name)
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
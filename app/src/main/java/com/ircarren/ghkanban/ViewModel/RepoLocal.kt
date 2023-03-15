package com.ircarren.ghkanban.ViewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import com.ircarren.ghkanban.models.Repo
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RepoLocal(application: Application) : ViewModel() {

    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(application)

    private val _repoIdsLocal = MutableLiveData<List<String>>()
    val repoIdsLocal: LiveData<List<String>> = _repoIdsLocal
    private val mapRepo: MutableMap<String, String> = mutableMapOf()

    // Guarda el repositorio en el shared preferences
    fun saveRepoLocal(repo: Repo) {

        _repoIdsLocal.value =
            (_repoIdsLocal.value?.plus(repo.name) ?: listOf(repo.name)) as List<String>?
        _repoIdsLocal.value?.forEach {
            if (it != null) {
                mapRepo[it] = Json.encodeToString(it)
            }
        }
        sharedPref.edit().putString("RepoIdsLocal", Json.encodeToString(mapRepo)).apply()
        loadRepoLocal()
    }

    fun loadRepoLocal() {
        val json = sharedPref.getString("RepoIdsLocal", "[]")

        if (json != null && json != "[]") {
            // json
            val map = Json.decodeFromString<Map<String, String>>(json)
            map.forEach {
                _repoIdsLocal.value =
                    (_repoIdsLocal.value?.plus(it.key) ?: listOf(it.key)) as List<String>?
            }
        } else {

        }

        //_repoIdsLocal.value = json?.let { Json.decodeFromString(it) }
    }

    fun deleteOneRefRepoLocal(RemoveRepo: String) {
        _repoIdsLocal.value = _repoIdsLocal.value?.minus(RemoveRepo)
        if (_repoIdsLocal.value == null) {
            _repoIdsLocal.value?.forEach {
                if (it != null) {
                    mapRepo[it] = Json.encodeToString(it)
                }
            }
        }
        sharedPref.edit().remove("RepoIdsLocal").apply()
        sharedPref.edit().putString("RepoIdsLocal", Json.encodeToString(mapRepo)).apply()
        loadRepoLocal()
    }

}
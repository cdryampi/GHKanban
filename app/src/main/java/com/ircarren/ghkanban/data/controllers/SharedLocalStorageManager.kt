package com.ircarren.ghkanban.data.controllers

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.ircarren.ghkanban.models.Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SharedLocalStorageManager(application: Application) {
    // clase que maneja el shared preferences
    private val sharedPref = PreferenceManager.getDefaultSharedPreferences(application)


    private val mapRepo: MutableMap<String, String> = mutableMapOf()
    private val _repoIdsLocal = MutableLiveData<List<String>>() // repositorio

    // Guarda el repositorio en el shared preferences
    fun saveRepoLocal(repo: String) {

        _repoIdsLocal.value =
            (_repoIdsLocal.value?.plus(repo) ?: listOf(repo)) as List<String>?

        mapRepo.clear()
        _repoIdsLocal.value?.forEach {
            if (it != null) {
                mapRepo[it] = Json.encodeToString(it)
            }
        }
        sharedPref.edit().putString("RepoIdsLocal", Json.encodeToString(mapRepo)).apply()
        loadRepoLocal()
    }

    // Carga el repositorio en el shared preferences
    fun loadRepoLocal() {

        // repository en abtraido
        val json = sharedPref.getString("RepoIdsLocal", "[]")

        if (json != null && json != "[]") {
            // json
            val map = Json.decodeFromString<Map<String, String>>(json)
            map.forEach {
                _repoIdsLocal.value =
                    (_repoIdsLocal.value?.plus(it.key) ?: listOf(it.key)) as List<String>?
            }
            _repoIdsLocal.value?.distinct()
        }

    }

    // Elimina el repositorio en el shared preferences
    fun deleteOneRefRepoLocal(RemoveRepo: String) {
        _repoIdsLocal.value = _repoIdsLocal.value?.minus(RemoveRepo)
        if (_repoIdsLocal.value == null) {
            clearMapRepo()
            _repoIdsLocal.value?.forEach {

                if (it != null) {
                    mapRepo[it] = Json.encodeToString(it)
                }
            }
        }else{
            _repoIdsLocal.value?.forEach {
                clearMapRepo()
                if (it != null) {
                    mapRepo[it] = Json.encodeToString(it)
                }
            }
        }
        if (_repoIdsLocal.value?.isEmpty() == true || _repoIdsLocal.value?.size == 1){
            sharedPref.edit().remove("RepoIdsLocal").apply()
        }else{
            sharedPref.edit().remove("RepoIdsLocal").apply()
            mapRepo.values.distinct()
            sharedPref.edit().putString("RepoIdsLocal", Json.encodeToString(mapRepo)).apply()
        }

        loadRepoLocal()
    }
    // limpiar
    fun clearMapRepo() {
        mapRepo.clear()
    }
    // get repositorio en el shared preferences
    fun getRepoLocal(): List<String>? {
        _repoIdsLocal.value = _repoIdsLocal.value?.distinct()
        return _repoIdsLocal.value
    }
}
package com.ircarren.ghkanban.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repo(
    @SerialName("name") val name: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("language") val language: String? = null,
    @SerialName("token") val token: String? = null
)
// clase que se encarga de guardar los datos de un repositorio
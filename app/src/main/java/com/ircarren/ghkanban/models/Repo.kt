package com.ircarren.ghkanban.models

data class Repo( val name: String, val description: String, val language: String, val token:String ?= null)
// clase que se encarga de guardar los datos de un repositorio
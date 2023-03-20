package com.ircarren.ghkanban.ui

open class MyRepoEvent {
    data class OnExplore(val next: Boolean) : MyRepoEvent()
    data class OnDelete(val next: Boolean) : MyRepoEvent()
}
package com.ircarren.ghkanban.models

import com.ircarren.ghkanban.data.enums.IssueStatus

data class Issue(
    val title: String ? = null,
    val htmlUrl: String ? = null,
    val state: String ? = null,
    val body: String ? = null,
    var status: IssueStatus? = IssueStatus.BACKLOG
)

package com.ircarren.ghkanban.models

import com.ircarren.ghkanban.data.enums.IssueStatus

data class Issue(
    var id: Int ? = null,
    var title: String ? = null,
    val htmlUrl: String ? = null,
    var state: String ? = null,
    var body: String ? = null
)

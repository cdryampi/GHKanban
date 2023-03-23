package com.ircarren.ghkanban.data.localDataBase.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ircarren.ghkanban.data.enums.IssueStatus


@Entity
data class Issue(
    // key for issue combination + issue_id
    @PrimaryKey var id: Int,
    @ColumnInfo(name="title") var title: String,
    @ColumnInfo(name="body") var body: String,
    @ColumnInfo(name="status") var status: String,
    @ColumnInfo(name="repo") var repo: String
)

package com.ircarren.ghkanban.data.localDataBase.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ircarren.ghkanban.data.enums.IssueStatus


@Entity
data class Issue(
    @PrimaryKey val title: String,
    @ColumnInfo(name="body") val body: String,
    @ColumnInfo(name="status") val status: String,
    @ColumnInfo(name="repo") val repo: String
)

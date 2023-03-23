package com.ircarren.ghkanban.data.localDataBase.appDataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ircarren.ghkanban.data.localDataBase.dao.Issue

@Database(entities = [Issue::class], version = 3)
abstract class AppDataBase: RoomDatabase() {
    abstract fun issueDao(): com.ircarren.ghkanban.data.localDataBase.entity.IssueDao
}
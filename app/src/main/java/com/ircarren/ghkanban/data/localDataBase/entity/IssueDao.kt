package com.ircarren.ghkanban.data.localDataBase.entity

import androidx.room.*
import com.ircarren.ghkanban.data.localDataBase.dao.Issue

@Dao
interface IssueDao {

    // select
    @Query("SELECT * FROM issue")
    suspend fun getAll(): List<Issue>

    // select by repo
    @Query("SELECT * FROM issue WHERE repo = :repo")
    suspend fun loadAllByRepo(repo: String): List<Issue>
    @Query("SELECT * FROM issue WHERE id = :id")
    suspend fun loadById(id: Int): Issue

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertOne(issue: Issue)

    @Query("DELETE FROM issue WHERE repo = :repo")
    suspend fun deleteAllByRepo(repo: String)

    @Update
    suspend fun updateOne(issue: Issue)

}
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

    @Query("INSERT INTO issue (title, body, status, repo) VALUES (:title, :body, :status, :repo)")
    suspend fun insertOne(title: String, body: String, status: String, repo: String)

    @Query("DELETE FROM issue WHERE repo = :repo")
    suspend fun deleteAllByRepo(repo: String)

    @Query("UPDATE issue SET status = :status WHERE title = :title AND repo = :repo")
    suspend fun updateOne(title: String, status: String, repo: String)
/*
    // update
    @Update
    fun updateIssue(issue: Issue)

    // insert one
    @Insert
    suspend fun insert(issue: Issue)

    // insert all
    @Insert


    // delete
    @Delete
    suspend fun delete(user: Issue)*/
}
package com.example.taskb.repository.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repo_table")
data class LocalRepo(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "updated_at")
    val lastUpdate: String,

    @ColumnInfo(name = "stargazers_count")
    val stars: Int,

    @ColumnInfo(name = "language")
    val language: String?,

    @ColumnInfo(name = "html_url")
    val htmlUrl: String,

    @ColumnInfo(name = "login")
    val login: String)
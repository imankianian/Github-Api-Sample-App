package com.example.taskb.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskb.repository.local.model.LocalUser
import com.example.taskb.repository.local.dao.UserDao
import com.example.taskb.repository.local.model.LocalRepo

@Database(entities = [LocalUser::class, LocalRepo::class], version = 1, exportSchema = false)
abstract class GitHubDatabase: RoomDatabase() {

    abstract val userDao: UserDao
}
package com.example.taskb.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskb.repository.local.model.LocalUser

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<LocalUser>)

    @Query("SELECT * FROM user_table")
    suspend fun getUsers(): List<LocalUser>
}
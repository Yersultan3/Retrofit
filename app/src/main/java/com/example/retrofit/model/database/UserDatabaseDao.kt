package com.example.retrofit.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDatabaseDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM Users_table ORDER BY userId DESC")
    fun getAllUsers(): LiveData<List<User>>

    @Query("DELETE FROM Users_table")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM Users_table WHERE user_name LIKE :userName")
    suspend fun getUsername(userName: String): User?
}
package com.example.myapplicationgiuaky.database

import androidx.room.*
import com.example.myapplicationgiuaky.Model.user
@Dao
interface USER_DAO {
    @Query("SELECT * FROM user_table ORDER BY id ASC")
    suspend fun getAllNotes(): List<user>

    @Query("SELECT * FROM user_table WHERE username LIKE :username")
    suspend fun findNoteByTitle(username: String): user

    @Insert
    suspend fun insert(user: user)

    @Delete
    suspend fun delete(user: user)

    @Update
    suspend fun update(user: user)

}
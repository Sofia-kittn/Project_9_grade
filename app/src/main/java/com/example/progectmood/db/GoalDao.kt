package com.example.progectmood.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GoalDao {
    @Query("SELECT * FROM goals")
    fun getAll(): List<Goal>

    @Insert
    fun insertAll(vararg goals: Goal)

    @Update
    fun updateGoal(goal: Goal)

    @Delete
    fun delete(goal: Goal)
}
package com.example.foodtracker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_table")
    fun getAll(): Flow<List<FoodEntity>>

    @Query("DELETE FROM food_table")
    fun deleteAll()

    @Insert
    fun insert(food: FoodEntity)
}
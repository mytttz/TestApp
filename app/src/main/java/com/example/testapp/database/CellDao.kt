package com.example.testapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CellDao {
    @Query("SELECT * FROM Cell")
    fun getAllCell(): List<Cell>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCell(cell: Cell)

    @Update
    fun updateCell(cell: Cell)
}

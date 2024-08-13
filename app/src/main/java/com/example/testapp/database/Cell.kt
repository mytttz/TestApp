package com.example.testapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Cell",
)
data class Cell(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val stateCell: String? = null,
    var emoji: String? = null,
    var description: String? = null,
    var background: Int? = null,
    var alreadyTaken: Boolean? = null
)
package com.example.spacex.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ships_table")
data class Ship(
    @PrimaryKey
    val id:String,
    val image:String?,
    val legacy:String?
)
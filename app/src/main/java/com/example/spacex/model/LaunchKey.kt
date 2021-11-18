package com.example.spacex.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launch_key_table")
data class LaunchKey (
    @PrimaryKey
    val id: String,
    val prevKey: Int?,
    val nextKey: Int?
    )

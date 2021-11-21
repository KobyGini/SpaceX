package com.example.spacex.data.local.shipdao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ship_key_table")
data class ShipKey (
    @PrimaryKey
    val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)

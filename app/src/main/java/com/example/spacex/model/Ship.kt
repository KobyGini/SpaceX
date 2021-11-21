package com.example.spacex.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ships_table")
data class Ship(
    @PrimaryKey
    val id:String,
    val image:String?,
    @SerializedName("legacy_id")
    val legacy:String?
)
package com.example.spacex.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launch_table")
data class LaunchLocal  (
    @PrimaryKey
    val id:String,
    val missionName:String?,
    val launchYear:String?,
    val missionPatch:String?,
    val wikipedia :String?,
    val details:String?,
    val shipsIds:List<String>?,
)
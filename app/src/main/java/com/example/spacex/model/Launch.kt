package com.example.spacex.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "launch_table")
data class Launch  (
    @PrimaryKey
    val id:String,
    val missionName:String?,
    val launchYear:String?,
    val missionPatch:String?,
    val wikipedia :String?,
    val details:String?
)
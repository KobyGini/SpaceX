package com.example.spacex.model

data class Launch (
    val id:String,
    val missionName:String?,
    val launchYear:String?,
    val missionPatch:String?,
    val wikipedia :String?,
    val details:String?,
    val ships:List<Ship>?,
)
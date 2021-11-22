package com.example.spacex.data.remote.model

import com.google.gson.annotations.SerializedName

data class LaunchResponse(
    @SerializedName("flight_number")
    val id:String,

    @SerializedName("name")
    val missionName:String,

    @SerializedName("launch_year")
    val launchYear:String,

    @SerializedName("mission_patch")
    val missionPatch:String,

    @SerializedName("links")
    val linksResponse:LinksResponse,

    @SerializedName("fairings")
    val fairings: FairingsResponse,

    val details: String,
    val article: String,
    val ships:ArrayList<String>?

)

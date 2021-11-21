package com.example.spacex.data.remote.model

import com.google.gson.annotations.SerializedName

data class ShipResponse (
    val id:String,
    val image:String?,
    @SerializedName("legacy_id")
    val legacyName:String?
)
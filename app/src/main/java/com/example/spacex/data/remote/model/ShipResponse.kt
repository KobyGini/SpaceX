package com.example.spacex.data.remote.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ShipResponse (
    @PrimaryKey
    val id:String,
    val image:String?,
    @SerializedName("legacy_id")
    val legacyName:String?
)
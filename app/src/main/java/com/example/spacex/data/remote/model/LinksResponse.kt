package com.example.spacex.data.remote.model

import com.google.gson.annotations.SerializedName

data class LinksResponse (
    @SerializedName("patch")
    val patch:PatchResponse,
    val wikipedia:String

    )
package com.example.spacex.data.remote

import com.example.spacex.data.remote.model.LaunchResponse
import retrofit2.http.GET

interface SpaceXService {

    @GET("launches")
    suspend fun getSpaceXLaunched(): List<LaunchResponse>
}
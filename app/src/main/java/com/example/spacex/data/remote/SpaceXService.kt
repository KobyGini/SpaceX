package com.example.spacex.data.remote

import com.example.spacex.data.remote.model.LaunchResponse
import com.example.spacex.data.remote.model.ShipResponse
import com.example.spacex.model.Ship
import retrofit2.http.GET

interface SpaceXService {

    @GET("launches")
    suspend fun getSpaceXLaunched(): List<LaunchResponse>

    @GET("ships")
    suspend fun getSpaceXShips(): List<ShipResponse>
}
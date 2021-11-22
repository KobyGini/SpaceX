package com.example.spacex.util

import com.example.spacex.data.remote.model.LaunchResponse
import com.example.spacex.data.remote.model.ShipResponse
import com.example.spacex.model.Launch
import com.example.spacex.model.Ship

object Mapper {

    fun launchResponseToLaunchModel(launchResponse: LaunchResponse,shipList:List<Ship>?):Launch{
       return Launch(
            launchResponse.id,
            launchResponse.missionName,
            launchResponse.launchYear,
            launchResponse.linksResponse.patch.small,
            launchResponse.linksResponse.wikipedia,
            launchResponse.details,
           shipList
       )

    }

    fun shipsResponseToShipsModel(shipResponse: ShipResponse): Ship {
        return Ship(
            shipResponse.id,
            shipResponse.image,
            shipResponse.legacyName,
        )
    }
}
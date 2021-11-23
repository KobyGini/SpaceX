package com.example.spacex.util

import com.example.spacex.data.remote.model.LaunchResponse
import com.example.spacex.data.remote.model.ShipResponse
import com.example.spacex.data.local.model.LaunchLocal
import com.example.spacex.model.Launch
import com.example.spacex.model.Ship

object Mapper {

    fun launchResponseToLaunchModel(launchResponse: LaunchResponse): LaunchLocal {
       return LaunchLocal(
            launchResponse.id,
            launchResponse.missionName,
            launchResponse.launchYear,
            launchResponse.linksResponse.patch.small,
            launchResponse.linksResponse.wikipedia,
            launchResponse.details,
            launchResponse.ships
       )

    }

    fun launchLocalToLaunchModel(launchLocal: LaunchLocal,ships : List<Ship>?): Launch {
        return Launch(
            launchLocal.id,
            launchLocal.missionName,
            launchLocal.launchYear,
            launchLocal.missionPatch,
            launchLocal.wikipedia,
            launchLocal.details,
            ships
        )

    }



    fun shipsResponseToShipsModel(shipResponse: ShipResponse): Ship {
        return Ship(
            shipResponse.id,
            shipResponse.image,
            shipResponse.legacyName,
        )
    }

    fun shipsResponseToShipsModel(shipResponse: List<ShipResponse>): List<Ship> {

        val ships = shipResponse.map {  shipResponse->
            Ship(
                shipResponse.id,
                shipResponse.image,
                shipResponse.legacyName,
            )
        }
        return ships
    }

    fun launchResponsesToLaunchLocals(launches: List<LaunchResponse>): List<LaunchLocal> {
        val launchesLocal = launches.map {
            LaunchLocal(
                it.id,
                it.missionName,
                it.launchYear,
                it.missionPatch,
                it.linksResponse.wikipedia,
                it.details,
                it.ships
            )
        }
        return launchesLocal
    }


}
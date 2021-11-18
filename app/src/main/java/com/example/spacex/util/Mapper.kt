package com.example.spacex.util

import com.example.spacex.data.remote.model.LaunchResponse
import com.example.spacex.model.Launch

object Mapper {

    fun launchResponseToLaunchModel(launchResponse: LaunchResponse):Launch{
       return Launch(
            launchResponse.id,
            launchResponse.missionName,
            launchResponse.launchYear,
            launchResponse.linksResponse.patch.small,
            launchResponse.linksResponse.wikipedia,
            launchResponse.details)
    }
}
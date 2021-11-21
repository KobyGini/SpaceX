package com.example.spacex.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.spacex.data.local.SpaceXDb
import com.example.spacex.data.remote.LaunchRemoteMediator
import com.example.spacex.data.remote.ShipRemoteMediator
import com.example.spacex.data.remote.SpaceXService
import com.example.spacex.model.Launch
import com.example.spacex.model.Ship
import com.example.spacex.util.Constants.DEFAULT_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository
@Inject constructor(
    private val appDatabase: SpaceXDb,
    private val retrofit: SpaceXService
) {

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }

    @ExperimentalPagingApi
    fun getLaunchPagingData(): Flow<PagingData<Launch>> {
            val pagingSourceFactory = { appDatabase.launchedDao().getLaunchesList() }
            return Pager(
                config = getDefaultPageConfig(),
                pagingSourceFactory = pagingSourceFactory,
                remoteMediator = LaunchRemoteMediator(
                    retrofit,
                    appDatabase,
                )
            ).flow
    }

    fun getLaunchDataById(id: String): Flow<Launch> {
        return appDatabase.launchedDao().getLaunchesById(id)
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getShipPagingData(): Flow<PagingData<Ship>> {
        val pagingSourceFactory = { appDatabase.shipDao().getShipList() }
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = ShipRemoteMediator(
                retrofit,
                appDatabase,
            )
        ).flow
    }

    fun getShipDataById(id: String): Flow<Ship> {
        return appDatabase.shipDao().getShipById(id)
    }
}
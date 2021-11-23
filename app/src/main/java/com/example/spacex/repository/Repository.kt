package com.example.spacex.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.spacex.data.local.SpaceXDb
import com.example.spacex.data.remote.LaunchRemoteMediator
import com.example.spacex.data.remote.ShipRemoteMediator
import com.example.spacex.data.remote.SpaceXService
import com.example.spacex.data.local.model.LaunchLocal
import com.example.spacex.model.Launch
import com.example.spacex.model.Ship
import com.example.spacex.util.Constants.DEFAULT_PAGE_SIZE
import com.example.spacex.util.Mapper
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
    fun getLaunchPagingData(): Flow<PagingData<LaunchLocal>> {
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

    suspend fun getLaunchDataById(id: String): Launch {

        val launchLocal = appDatabase.launchedDao().getLaunchesById(id)
        val launchShips = launchLocal.shipsIds?.let { appDatabase.shipDao().getShipByIds(it) }
        return Mapper.launchLocalToLaunchModel(launchLocal,launchShips)

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

    suspend fun updateContents() : List<LaunchLocal>? {

        //Get launches from retrofit service
        val retrofitLaunches = retrofit.getSpaceXLaunched()
        val retrofitShips = retrofit.getSpaceXShips()

        //Map retrofit launches to local launches
        val launchFromRemote = Mapper.launchResponsesToLaunchLocals(retrofitLaunches)
        val shipLocal = Mapper.shipsResponseToShipsModel(retrofitShips)

        //Get all launches from local db
        val launchesLocal = appDatabase.launchedDao().getLaunchList()

        //Insert launches to db
        appDatabase.launchedDao().insertAll(launchesLocal)
        appDatabase.shipDao().insertAll(shipLocal)

        //Compare new launches and old launches and get the new one's
        return if (launchesLocal.isNotEmpty()) {
            val newLaunches = launchFromRemote.filter {
                launchLocal -> !launchesLocal.contains(launchLocal)
            }
            newLaunches
        } else {
            null
        }
    }

}
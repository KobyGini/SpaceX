package com.example.spacex.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bumptech.glide.load.HttpException
import com.example.spacex.data.local.SpaceXDb
import com.example.spacex.data.local.launchdao.LaunchKey
import com.example.spacex.model.Ship
import com.example.spacex.util.Mapper
import java.io.IOException

@ExperimentalPagingApi
class ShipRemoteMediator(
    private val spaceXService: SpaceXService,
    private val spaceXDatabase: SpaceXDb
) : RemoteMediator<Int, Ship>() {

    override suspend fun initialize(): InitializeAction {
        // Require that remote REFRESH is launched on initial load and succeeds before launching
        // remote PREPEND / APPEND.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }


    override suspend fun load(loadType: LoadType, state: PagingState<Int, Ship>): MediatorResult {
        //Get page number
        val page = when (loadType) {
            LoadType.REFRESH -> {
                null
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys?.nextKey == null){
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                1
            }
        }

        try {
            val response = spaceXService.getSpaceXShips()

            val isEndOfList = true
            spaceXDatabase.withTransaction {
                //clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    spaceXDatabase.shipsKeysDao().clearLaunchesKeys()
                    spaceXDatabase.shipDao().deleteAllShip()
                }

                val prevKey = null
                val nextKey = null
                val keys = response.map {
                    LaunchKey(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }

                val shipModelList = response.map {
                    Mapper.shipsResponseToShipsModel(it)
                }

                spaceXDatabase.shipsKeysDao().insertAll(keys)
                spaceXDatabase.shipDao().insertAll(shipModelList)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    /**
     * get the last remote key inserted which had the data
     */
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Ship>): LaunchKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { doggo -> spaceXDatabase.shipsKeysDao().remoteKeysLaunchId(doggo.id) }
    }

}

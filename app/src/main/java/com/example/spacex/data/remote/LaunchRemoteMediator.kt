package com.example.spacex.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bumptech.glide.load.HttpException
import com.example.spacex.data.local.SpaceXDb
import com.example.spacex.data.local.model.LaunchLocal
import com.example.spacex.data.local.launchdao.LaunchKey
import com.example.spacex.util.Mapper
import java.io.IOException

@ExperimentalPagingApi
class LaunchRemoteMediator(
    private val spaceXService: SpaceXService,
    private val spaceXDatabase: SpaceXDb
) : RemoteMediator<Int, LaunchLocal>() {

    override suspend fun initialize(): InitializeAction {
        // Require that remote REFRESH is launched on initial load and succeeds before launching
        // remote PREPEND / APPEND.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, LaunchLocal>
    ): MediatorResult {

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
                println(remoteKeys)
                if (remoteKeys?.nextKey == null){
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                1
            }
        }

        try {
            val response = spaceXService.getSpaceXLaunched()
            val set = HashSet<String>()

            response.forEach{
                if(it.ships != null){
                    set.addAll(it.ships)
                }
            }

            spaceXDatabase.withTransaction {

                val count = spaceXDatabase.shipDao().getShipCountByIds(set.toList())
                if(count != set.size){
                    //TODO: HANDLE CASE WHEN SHIPS IS NOT IN DB
                    println("Ships is not in db")
                }else{

                }
            }

            val isEndOfList = true
            spaceXDatabase.withTransaction {
                //clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    spaceXDatabase.launchedKeysDao().clearLaunchesKeys()
                    spaceXDatabase.launchedDao().deleteLaunches()
                }

                val prevKey = null
                val nextKey = null
                val keys = response.map {
                    LaunchKey(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }

                val launchModelList = response.map { launchResponse ->
                   Mapper.launchResponseToLaunchModel(launchResponse)
                }

                spaceXDatabase.launchedKeysDao().insertAll(keys)
                spaceXDatabase.launchedDao().insertAll(launchModelList)
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
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, LaunchLocal>): LaunchKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { launch -> spaceXDatabase.launchedKeysDao().remoteKeysLaunchId(launch.id) }
    }
}
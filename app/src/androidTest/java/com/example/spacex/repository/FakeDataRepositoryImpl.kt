package com.example.spacex.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.spacex.data.local.SpaceXDb
import com.example.spacex.data.local.model.LaunchLocal
import com.example.spacex.model.Launch
import com.example.spacex.model.Ship
import com.example.spacex.util.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FakeDataRepositoryImpl
    @Inject constructor(
       private val appDatabase : SpaceXDb
    ): DataRepository {

    override fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = Constants.DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }

    @ExperimentalPagingApi
    override fun getLaunchPagingData(): Flow<PagingData<LaunchLocal>> {

        appDatabase.launchedDao().insert(LaunchLocal("1","","","","","", listOf()))

        val pagingSourceFactory = { appDatabase.launchedDao().getLaunchesList() }

        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }

    override suspend fun getLaunchDataById(id: String): Launch {
        TODO("Not yet implemented")
    }

    override fun getShipPagingData(): Flow<PagingData<Ship>> {
        TODO("Not yet implemented")
    }

    override fun getShipDataById(id: String): Flow<Ship> {
        TODO("Not yet implemented")
    }

    override suspend fun updateContents(): List<LaunchLocal> {
        TODO("Not yet implemented")
    }
}
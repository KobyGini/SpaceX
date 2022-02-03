package com.example.spacex.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.spacex.data.local.model.LaunchLocal
import com.example.spacex.model.Launch
import com.example.spacex.model.Ship
import kotlinx.coroutines.flow.Flow

interface DataRepository {


    fun getDefaultPageConfig(): PagingConfig

    @ExperimentalPagingApi
    fun getLaunchPagingData(): Flow<PagingData<LaunchLocal>>

    suspend fun getLaunchDataById(id: String): Launch

    @OptIn(ExperimentalPagingApi::class)
    fun getShipPagingData(): Flow<PagingData<Ship>>

    fun getShipDataById(id: String): Flow<Ship>

    suspend fun updateContents() : List<LaunchLocal>
}
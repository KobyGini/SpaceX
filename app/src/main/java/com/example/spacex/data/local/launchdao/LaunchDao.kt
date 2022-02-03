package com.example.spacex.data.local.launchdao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.example.spacex.data.local.model.LaunchLocal

@Dao
interface LaunchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(launchLocalModels: List<LaunchLocal>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(launchLocalModel: LaunchLocal)

    @Query("DELETE FROM launch_table")
    suspend fun deleteLaunches()

    @Query("SELECT * FROM launch_table")
    fun getLaunchesList() : PagingSource<Int, LaunchLocal>

    @Query("SELECT * FROM launch_table")
    fun getLaunchList() : List<LaunchLocal>

    @Query("SELECT * FROM launch_table WHERE id= :id")
    suspend fun getLaunchesById(id:String) : LaunchLocal

    @Query("SELECT * FROM launch_table")
    fun getLaunchesModel() : LiveData<List<LaunchLocal>>
}
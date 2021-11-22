package com.example.spacex.data.local.launchdao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.example.spacex.model.Launch
import kotlinx.coroutines.flow.Flow

@Dao
interface LaunchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(launches: List<Launch>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(launch: Launch)

    @Query("DELETE FROM launch_table")
    suspend fun deleteLaunches()

    @Query("SELECT * FROM launch_table")
    fun getLaunchesList() : PagingSource<Int, Launch>

    @Query("SELECT * FROM launch_table WHERE id= :id")
    fun getLaunchesById(id:String) : Flow<Launch>

    @Query("SELECT * FROM launch_table")
    fun getLaunchesModel() : LiveData<List<Launch>>

}
package com.example.spacex.data.local.launchdao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LaunchKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<LaunchKey>)

    @Query("SELECT * FROM launch_key_table WHERE id = :id")
    suspend fun remoteKeysLaunchId(id: String): LaunchKey

    @Query("DELETE FROM launch_key_table")
    suspend fun clearLaunchesKeys()
}
package com.example.spacex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spacex.model.LaunchKey

@Dao
interface LaunchKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<LaunchKey>)

    @Query("SELECT * FROM launch_key_table WHERE id = :id")
    suspend fun remoteKeysLaunchId(id: String): LaunchKey

    @Query("DELETE FROM launch_key_table")
    suspend fun clearLaunchesKeys()
}
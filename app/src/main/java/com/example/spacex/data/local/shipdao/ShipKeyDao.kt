package com.example.spacex.data.local.shipdao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spacex.data.local.launchdao.LaunchKey

@Dao
interface ShipKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<LaunchKey>)

    @Query("SELECT * FROM launch_key_table WHERE id = :id")
    suspend fun remoteKeysShipId(id: String): LaunchKey

    @Query("DELETE FROM launch_key_table")
    suspend fun clearShipKeys()
}
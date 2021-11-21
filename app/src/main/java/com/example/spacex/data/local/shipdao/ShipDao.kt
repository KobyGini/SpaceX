package com.example.spacex.data.local.shipdao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.spacex.model.Ship
import kotlinx.coroutines.flow.Flow

@Dao
interface ShipDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ships: List<Ship>): LongArray

    @Query("DELETE FROM ships_table")
    suspend fun deleteAllShip()

    @Query("SELECT * FROM ships_table")
    fun getShipList() : PagingSource<Int, Ship>

    @Query("SELECT * FROM ships_table WHERE id = :id")
    fun getShipById(id:String) : Flow<Ship>
}
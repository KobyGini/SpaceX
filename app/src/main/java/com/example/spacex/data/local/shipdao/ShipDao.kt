package com.example.spacex.data.local.shipdao

import androidx.lifecycle.LiveData
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ships: Ship)

    @Query("DELETE FROM ships_table")
    suspend fun deleteAllShip()

    @Query("SELECT * FROM ships_table")
    fun getShipList() : PagingSource<Int, Ship>

    @Query("SELECT * FROM ships_table")
    fun getShipListLiveData() : LiveData<List<Ship>>

    @Query("SELECT * FROM ships_table WHERE shipId = :id")
    fun getShipById(id:String) : Flow<Ship>

    @Query("SELECT * FROM ships_table WHERE shipId IN (:id)")
    suspend fun getShipByIds(id:List<String>) : List<Ship>

    @Query("SELECT COUNT() FROM ships_table WHERE shipId IN (:id)")
    fun getShipCountByIds(id:List<String>) : Int
}
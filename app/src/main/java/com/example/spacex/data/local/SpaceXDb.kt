package com.example.spacex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.spacex.data.local.launchdao.DataTypeConvertor
import com.example.spacex.data.local.launchdao.LaunchDao
import com.example.spacex.data.local.launchdao.LaunchKeyDao
import com.example.spacex.data.local.shipdao.ShipDao
import com.example.spacex.data.local.shipdao.ShipKeyDao
import com.example.spacex.model.Launch
import com.example.spacex.data.local.launchdao.LaunchKey
import com.example.spacex.model.Ship
import com.example.spacex.data.local.shipdao.ShipKey

@Database(entities = [
    Launch::class,
    LaunchKey::class,
    Ship::class,
    ShipKey::class],
    version =1)
@TypeConverters(DataTypeConvertor::class)
abstract class SpaceXDb : RoomDatabase() {
    abstract fun launchedDao() : LaunchDao
    abstract fun launchedKeysDao() : LaunchKeyDao
    abstract fun shipDao() : ShipDao
    abstract fun shipsKeysDao() : ShipKeyDao
}
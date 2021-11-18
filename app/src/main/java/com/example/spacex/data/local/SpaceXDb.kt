package com.example.spacex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.spacex.model.Launch
import com.example.spacex.model.LaunchKey


@Database(entities = [Launch::class, LaunchKey::class], version =1)
abstract class SpaceXDb : RoomDatabase() {
    abstract fun launchedDao() : LaunchDao
    abstract fun launchedKeysDao() : LaunchKeyDao
}
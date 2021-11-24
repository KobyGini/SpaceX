package com.example.spacex.data.local.launchdao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.spacex.data.local.SpaceXDb
import com.example.spacex.data.local.shipdao.ShipDao
import com.example.spacex.getOrAwaitValue
import com.example.spacex.model.Ship
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ShipDaoTest {

    private lateinit var database: SpaceXDb
    private lateinit var shipDao: ShipDao

    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SpaceXDb::class.java
        ).allowMainThreadQueries().build()
        shipDao = database.shipDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertShipItem() = runBlockingTest {
        val ship = Ship(
            "5ea6ed2d080df4000697c901",
            "",
            "FlatIcon"
        )
        shipDao.insert(ship)
        val allShipsItem = shipDao.getShipListLiveData().getOrAwaitValue()
        Truth.assertThat(allShipsItem).contains(ship)
    }

    @Test
    fun getShipsByIds() = runBlockingTest{
        val shipsList = listOf("5ea6ed2d080df4000697c901")

        val ship = Ship(
            "5ea6ed2d080df4000697c901",
            "",
            "FlatIcon"
        )

        shipDao.insert(ship)

        val shipsByIds = shipDao.getShipByIds(shipsList)

        Truth.assertThat(shipsByIds).contains(ship)

    }
}
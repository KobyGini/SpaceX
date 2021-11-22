package com.example.spacex.data.local.launchdao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.spacex.data.local.SpaceXDb
import com.example.spacex.data.local.shipdao.ShipDao
import com.example.spacex.getOrAwaitValue
import com.example.spacex.model.Launch
import com.example.spacex.model.Ship
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
@SmallTest
class LaunchDaoTest {

    private lateinit var database: SpaceXDb
    private lateinit var launchDao: LaunchDao
    private lateinit var shipDao: ShipDao

    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SpaceXDb::class.java
        ).allowMainThreadQueries().build()
        launchDao = database.launchedDao()
        shipDao = database.shipDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertLaunchItem() = runBlockingTest {
        val shipsList = ArrayList<Ship>()
        shipsList.add(Ship("5ea6ed2d080df4000697c901","","FlatIcon"))

        val launch = Launch("1",
            "testName",
            "1992",
            "",
            "",
            "testDetails",
            shipsList
        )

        launchDao.insert(launch)

        val allLaunchItem = launchDao.getLaunchesModel().getOrAwaitValue()
        assertThat(allLaunchItem).contains(launch)
    }
}
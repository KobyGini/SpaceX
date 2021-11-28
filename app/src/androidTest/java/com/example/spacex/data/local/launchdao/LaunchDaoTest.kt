package com.example.spacex.data.local.launchdao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.spacex.data.local.SpaceXDb
import com.example.spacex.data.local.model.LaunchLocal
import com.example.spacex.data.local.shipdao.ShipDao
import com.example.spacex.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
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
        val shipsList = listOf("5ea6ed2d080df4000697c901")

        val launch = LaunchLocal(
            "1",
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

    @Test
    fun insertAllLaunchItem() = runBlocking {
        val shipsList = listOf("5ea6ed2d080df4000697c901")

        val launch1 = LaunchLocal(
            "1",
            "testName",
            "1992",
            "",
            "",
            "testDetails",
            shipsList
        )

        val launch2 = LaunchLocal(
            "2",
            "testName",
            "1992",
            "",
            "",
            "testDetails",
            shipsList
        )

        val launch3 = LaunchLocal(
            "3",
            "testName",
            "1992",
            "",
            "",
            "testDetails",
            shipsList
        )

        val launch4 = LaunchLocal(
            "3",
            "testName",
            "1992",
            "",
            "",
            "testDetails",
            shipsList
        )

        val launchArray = ArrayList<LaunchLocal>()
        launchArray.add(launch1)
        launchArray.add(launch2)
        launchArray.add(launch3)

        launchDao.insertAll(launchArray)
        val x = launchDao.insertAll(launchArray)

        assertThat(x).asList().doesNotContain(-1)
    }
}
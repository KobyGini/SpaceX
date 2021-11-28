package com.example.spacex.data.local.launchdao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.ExperimentalPagingApi
import androidx.test.filters.SmallTest
import com.example.spacex.data.local.SpaceXDb
import com.example.spacex.data.local.model.LaunchLocal
import com.example.spacex.data.local.shipdao.ShipDao
import com.example.spacex.getOrAwaitValue
import com.example.spacex.launchFragmentInHiltContainer
import com.example.spacex.ui.launcheslist.LaunchListFragment
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule
import org.mockito.Mockito
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
class LaunchDaoTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()


    @Inject
    @Named("test_db")
    lateinit var database: SpaceXDb

    private lateinit var launchDao: LaunchDao
    private lateinit var shipDao: ShipDao

    @Before
    fun setup(){
        hiltRule.inject()
        launchDao = database.launchedDao()
        shipDao = database.shipDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @ExperimentalPagingApi
    @Test
    fun abc(){
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<LaunchListFragment> {
            Navigation.setViewNavController(requireView(),navController)
        }
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
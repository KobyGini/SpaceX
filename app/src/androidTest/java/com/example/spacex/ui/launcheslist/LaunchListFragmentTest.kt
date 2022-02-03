package com.example.spacex.ui.launcheslist

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.ExperimentalPagingApi
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.example.spacex.R
import com.example.spacex.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

import org.mockito.Mockito.mock

@MediumTest
@HiltAndroidTest
@OptIn(ExperimentalPagingApi::class)
class LaunchListFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun checkFragmentRecyclerViewVisibility() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<LaunchListFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.launch_list_recyclerview)).check(matches(isDisplayed()))
    }

    @Test
    fun clickLaunchItemNavigateLaunchDetails() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<LaunchListFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.launch_list_recyclerview))
            .perform(
                actionOnItemAtPosition<LaunchPagingAdapter.LaunchViewHolder>(0, click())
            )

        Mockito.verify(navController).navigate(
            LaunchListFragmentDirections.actionLaunchListFragmentToLaunchDetailsFragment("1")
        )
    }
}
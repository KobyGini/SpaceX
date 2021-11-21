package com.example.spacex.ui.launchdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import com.bumptech.glide.Glide
import com.example.spacex.R
import com.example.spacex.model.Launch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_launch_details.*

@AndroidEntryPoint
class LaunchDetailsFragment : Fragment(R.layout.fragment_launch_details) {

    private val launchDetailsViewModel: LaunchDetailsViewModel by viewModels()

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val launchId = arguments
            ?.getString("launchId")

        launchId?.let {
            launchDetailsViewModel.getLaunchesByIdLive(it).observe(viewLifecycleOwner,{
                setLaunchDetails(it)
            })
        }
    }

    private fun setLaunchDetails(launch:Launch) {
        Glide.with(this)
            .load(launch.missionPatch)
            .placeholder(R.drawable.ic_baseline_image_not_supported)
            .error(R.drawable.ic_baseline_image_not_supported)
            .into(launch_details_image)
        launch_details_details.text = launch.details
        launch_details_name.text = launch.missionName
        launch_details_wikipedia.text = launch.wikipedia
    }
}
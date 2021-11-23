package com.example.spacex.ui.launchdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.bumptech.glide.Glide
import com.example.spacex.R
import com.example.spacex.model.Launch
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_launch_details.*

@AndroidEntryPoint
class LaunchDetailsFragment : Fragment(R.layout.fragment_launch_details) {

    private val launchDetailsViewModel: LaunchDetailsViewModel by viewModels()
    private val adapter = LaunchShipAdapter()
    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val launchId = arguments
            ?.getString("launchId")

        launchId?.let {
            launchDetailsViewModel.getLaunchesByIdLive(it)
        }

        launchDetailsViewModel.launch.observe(viewLifecycleOwner,{
            setLaunchDetails(it)
        })

        launchDetailsRecyclerView.adapter = adapter

        adapter.setOnClick {
           val action = LaunchDetailsFragmentDirections
               .actionLaunchDetailsFragmentToShipDetailsFragment(it.shipId)
            findNavController().navigate(action)
        }
    }

    private fun setLaunchDetails(launchModel: Launch) {
        Glide.with(this)
            .load(launchModel.missionPatch)
            .placeholder(R.drawable.ic_baseline_image_not_supported)
            .error(R.drawable.ic_baseline_image_not_supported)
            .into(launch_details_image)
        launch_details_details.text = launchModel.details
        launch_details_name.text = launchModel.missionName
        launch_details_wikipedia.text = launchModel.wikipedia
        adapter.submitList(launchModel.ships)
    }
}
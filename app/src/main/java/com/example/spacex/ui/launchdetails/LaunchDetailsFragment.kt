package com.example.spacex.ui.launchdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.bumptech.glide.Glide
import com.example.spacex.R
import com.example.spacex.databinding.FragmentLaunchDetailsBinding
import com.example.spacex.model.Launch
import com.example.spacex.util.BundleConstants.LAUNCH_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchDetailsFragment : Fragment() {

    private val launchDetailsViewModel: LaunchDetailsViewModel by viewModels()
    private val adapter = LaunchShipAdapter()

    private var _binding: FragmentLaunchDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val launchId = arguments
            ?.getString(LAUNCH_ID)

        launchId?.let {
            launchDetailsViewModel.getLaunchesByIdLive(it)
        }

        launchDetailsViewModel.launch.observe(viewLifecycleOwner,{
            setLaunchDetails(it)
        })

        binding.launchDetailsRecyclerview.adapter = adapter

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
            .into(binding.launchDetailsImage)
        binding.launchDetailsDetails.text = launchModel.details
        binding.launchDetailsName.text = launchModel.missionName
        binding.launchDetailsWikipedia.text = launchModel.wikipedia
        adapter.submitList(launchModel.ships)
    }
}
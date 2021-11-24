package com.example.spacex.ui.shipdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import com.bumptech.glide.Glide
import com.example.spacex.databinding.FragmentShipDetailsBinding
import com.example.spacex.model.Ship
import com.example.spacex.util.BundleConstants.SHIP_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShipDetailsFragment : Fragment(){

    private val shipDetailsViewModel: ShipDetailsViewModel by viewModels()

    private var _binding: FragmentShipDetailsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShipDetailsBinding.inflate(inflater, container, false)
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
            ?.getString(SHIP_ID)

        launchId?.let {
            shipDetailsViewModel.getShipByIdLive(it).observe(viewLifecycleOwner,{
                setShipDetails(it)
            })
        }
    }

    private fun setShipDetails(ship: Ship) {
        Glide.with(this).load(ship.image).into(binding.shipDetailsImage)
        binding.shipDetailsName.text = ship.legacy
    }
}
package com.example.spacex.ui.shipdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import com.bumptech.glide.Glide
import com.example.spacex.R
import com.example.spacex.model.Ship
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_ship_details.*

@AndroidEntryPoint
class ShipDetailsFragment : Fragment(R.layout.fragment_ship_details){

    private val shipDetailsViewModel: ShipDetailsViewModel by viewModels()

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val launchId = arguments
            ?.getString("shipId")

        launchId?.let {
            shipDetailsViewModel.getShipByIdLive(it).observe(viewLifecycleOwner,{
                setShipDetails(it)
            })
        }
    }

    private fun setShipDetails(ship: Ship) {
        Glide.with(this).load(ship.image).into(ship_details_image)
        ship_details_name.text = ship.legacy
    }
}
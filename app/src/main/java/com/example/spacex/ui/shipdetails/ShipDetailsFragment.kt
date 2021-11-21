package com.example.spacex.ui.shipdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.bumptech.glide.Glide
import com.example.spacex.R
import com.example.spacex.model.Ship
import kotlinx.android.synthetic.main.fragment_ship_details.*

class ShipDetailsFragment : Fragment(R.layout.fragment_ship_details){

    lateinit var shipDetailsViewModel: ShipDetailsViewModel

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shipDetailsViewModel = ViewModelProvider(requireActivity()).get(ShipDetailsViewModel::class.java)

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
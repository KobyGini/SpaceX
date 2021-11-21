package com.example.spacex.ui.shiplist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.example.spacex.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShipListFragment : Fragment(R.layout.fragment_ship_list){

    private val shipListViewModel: ShipListViewModel by viewModels()
    private lateinit var launchRecyclerView: RecyclerView
    lateinit var adapter: ShipListPagingAdapter

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView(view)

        shipListViewModel.getShipListLive().observe(viewLifecycleOwner, {
            adapter.submitData(lifecycle, it)
        })
    }

    private fun setUpRecyclerView(view: View) {
        launchRecyclerView = view.findViewById(R.id.launch_recyclerview)
        adapter = ShipListPagingAdapter()
        adapter.setOnLaunchClickListener{
            val action = ShipListFragmentDirections
                    .actionShipListFragmentToShipDetailsFragment(it.id)
            findNavController().navigate(action)
        }
        launchRecyclerView.adapter = adapter
    }
}
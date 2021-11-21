package com.example.spacex.ui.shiplist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.example.spacex.R

class ShipListFragment : Fragment(R.layout.fragment_ship_list){

    private lateinit var shipListViewModel: ShipListViewModel
    private lateinit var launchRecyclerView: RecyclerView
    lateinit var adapter: ShipListPagingAdapter

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shipListViewModel =
            ViewModelProvider(requireActivity()).get(ShipListViewModel::class.java)

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
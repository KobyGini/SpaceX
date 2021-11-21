package com.example.spacex.ui.launcheslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.example.spacex.R
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class LaunchListFragment : Fragment(R.layout.fragment_launch_list)
{
    private val launchedViewModel: LaunchListViewModel by viewModels()
    private lateinit var launchRecyclerView: RecyclerView
    lateinit var adapter: LaunchPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView(view)

        launchedViewModel.getLaunchesListLive().observe(viewLifecycleOwner, {
            adapter.submitData(lifecycle, it)
        })
    }

    private fun setUpRecyclerView(view: View) {
        launchRecyclerView = view.findViewById(R.id.launch_recyclerview)
        adapter = LaunchPagingAdapter()
        adapter.setOnLaunchClickListener{
            val action = LaunchListFragmentDirections.
            actionLaunchListFragmentToLaunchDetailsFragment(it.id)
            findNavController().navigate(action)
        }
        launchRecyclerView.adapter = adapter
    }
}
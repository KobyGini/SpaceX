package com.example.spacex.ui.launcheslist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.example.spacex.R
import com.example.spacex.model.Launch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class LaunchListFragment : Fragment(R.layout.fragment_launch_list)
{
    private lateinit var launchedViewModel: LaunchListViewModel
    private lateinit var launchRecyclerView: RecyclerView
    lateinit var adapter: LaunchPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchedViewModel =
            ViewModelProvider(requireActivity()).get(LaunchListViewModel::class.java)

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
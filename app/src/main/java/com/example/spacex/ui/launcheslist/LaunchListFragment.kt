package com.example.spacex.ui.launcheslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.example.spacex.databinding.FragmentLaunchListBinding
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class LaunchListFragment : Fragment()
{
    private val launchedViewModel: LaunchListViewModel by viewModels()
    private val adapter = LaunchPagingAdapter()

    private var _binding: FragmentLaunchListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        launchedViewModel.getLaunchesListLive().observe(viewLifecycleOwner, {
            adapter.submitData(lifecycle, it)
        })
    }

    private fun setUpRecyclerView() {
        adapter.setOnLaunchClickListener{
            val action = LaunchListFragmentDirections.
            actionLaunchListFragmentToLaunchDetailsFragment(it.id)
            findNavController().navigate(action)
        }
        binding.launchListRecyclerview.adapter = adapter
    }
}
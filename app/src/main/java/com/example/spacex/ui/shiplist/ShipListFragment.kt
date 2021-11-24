package com.example.spacex.ui.shiplist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.example.spacex.databinding.FragmentShipListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShipListFragment : Fragment(){

    private val shipListViewModel: ShipListViewModel by viewModels()
    private val adapter = ShipListPagingAdapter()

    private var _binding: FragmentShipListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShipListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        shipListViewModel.getShipListLive().observe(viewLifecycleOwner, {
            adapter.submitData(lifecycle, it)
        })
    }

    private fun setUpRecyclerView() {
        adapter.setOnLaunchClickListener{
            val action = ShipListFragmentDirections
                    .actionShipListFragmentToShipDetailsFragment(it.shipId)
            findNavController().navigate(action)
        }
        binding.shipListRecyclerview.adapter = adapter
    }
}
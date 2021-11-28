package com.example.spacex.ui.shiplist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.spacex.model.Ship
import com.example.spacex.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShipListViewModel
@Inject
constructor(
    private val repository: DataRepository
): ViewModel() {

    @ExperimentalPagingApi
    fun getShipListLive(): LiveData<PagingData<Ship>> {
        return repository.getShipPagingData().asLiveData()
    }
}
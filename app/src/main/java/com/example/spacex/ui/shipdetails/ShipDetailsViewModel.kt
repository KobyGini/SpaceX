package com.example.spacex.ui.shipdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.ExperimentalPagingApi
import com.example.spacex.model.Ship
import com.example.spacex.repository.DataRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShipDetailsViewModel
@Inject
constructor(
    private val repository: DataRepository
): ViewModel() {

    @ExperimentalPagingApi
    fun getShipByIdLive(id:String): LiveData<Ship> {
        return repository.getShipDataById(id).asLiveData()
    }
}
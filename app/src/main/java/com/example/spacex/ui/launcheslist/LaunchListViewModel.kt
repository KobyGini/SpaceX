package com.example.spacex.ui.launcheslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.spacex.data.local.model.LaunchLocal
import com.example.spacex.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel
@Inject
constructor(
    private val repository: DataRepository
):ViewModel() {

    @ExperimentalPagingApi
    fun getLaunchesListLive(): LiveData<PagingData<LaunchLocal>> {
        return repository.getLaunchPagingData().asLiveData()
    }
}
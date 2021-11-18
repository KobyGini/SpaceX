package com.example.spacex.ui.launcheslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.spacex.model.Launch
import com.example.spacex.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel
@Inject
constructor(
    private val repository: Repository
):ViewModel() {

    @ExperimentalPagingApi
    fun getLaunchesListLive(): LiveData<PagingData<Launch>> {
        return repository.getLaunchPagingData().asLiveData()
    }
}